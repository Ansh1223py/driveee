const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const HOST = '0.0.0.0';

const APK_PATH = path.join(__dirname, 'app/build/outputs/apk/debug/app-debug.apk');

const server = http.createServer((req, res) => {
  const url = new URL(req.url, `http://${req.headers.host}`);

  // Health check endpoint
  if (url.pathname === '/health' || url.pathname === '/ping') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ status: 'healthy', timestamp: new Date().toISOString() }));
    return;
  }

  // APK download endpoint
  if (url.pathname === '/download-apk' || url.pathname === '/api/download-apk') {
    if (fs.existsSync(APK_PATH)) {
      const stat = fs.statSync(APK_PATH);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Length': stat.size,
        'Content-Disposition': 'attachment; filename="drivee-app-debug.apk"'
      });
      const stream = fs.createReadStream(APK_PATH);
      stream.pipe(res);
    } else {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('APK not found. Please compile the Android project first.');
    }
    return;
  }

  // Serve static assets or index
  res.writeHead(200, {
    'Content-Type': 'text/html; charset=utf-8',
    'Cache-Control': 'no-cache, no-store, must-revalidate'
  });
  res.end(getHtmlContent());
});

function getHtmlContent() {
  return `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DRIVEE - On-Demand Chauffeurs for Your Personal Car</title>
  <meta name="description" content="Book screened, police-verified professional chauffeurs by the hour to drive your personal car in Mumbai. Live GPS tracking, ₹50L vehicle shield, and instant dispatch.">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800;900&display=swap" rel="stylesheet">
  <script src="https://cdn.tailwindcss.com"></script>
  <script>
    tailwind.config = {
      theme: {
        extend: {
          fontFamily: {
            sans: ['"Plus Jakarta Sans"', 'sans-serif'],
          },
          colors: {
            drivee: {
              bg: '#0B121E',
              surface: '#132034',
              surfaceLow: '#0E1726',
              surfaceHigh: '#1E2F4A',
              border: '#1E2E48',
              orange: '#FD651E',
              orangeHover: '#EA5510',
              navy: '#0B1C30',
              navyDark: '#071220',
              green: '#10B981',
              greenBg: '#064E3B',
              red: '#EF4444',
              redBg: '#450A0A',
              amber: '#F59E0B'
            }
          }
        }
      }
    }
  </script>
  <style>
    body {
      background-color: #080D16;
      color: #F8FAFC;
      font-family: 'Plus Jakarta Sans', sans-serif;
    }
    .custom-scroll::-webkit-scrollbar {
      width: 4px;
      height: 4px;
    }
    .custom-scroll::-webkit-scrollbar-thumb {
      background: #1E2E48;
      border-radius: 4px;
    }
    @keyframes pulse-ring {
      0% { transform: scale(0.95); opacity: 0.8; }
      50% { transform: scale(1.3); opacity: 0.2; }
      100% { transform: scale(0.95); opacity: 0.8; }
    }
    .pulse-ring {
      animation: pulse-ring 2.5s infinite ease-in-out;
    }
  </style>
</head>
<body class="min-h-screen flex flex-col items-center justify-start p-0 md:py-6 md:px-4 bg-[#080D16]">

  <!-- Main Container (Mobile App Frame Simulator) -->
  <div class="w-full max-w-md bg-drivee-bg md:rounded-3xl md:border md:border-drivee-border shadow-2xl overflow-hidden flex flex-col min-h-screen md:min-h-[850px] relative">
    
    <!-- Top Header -->
    <header class="bg-drivee-navy px-4 py-3 border-b border-drivee-border flex items-center justify-between sticky top-0 z-30">
      <div class="flex items-center space-x-2">
        <div class="w-7 h-7 bg-drivee-orange rounded-lg flex items-center justify-center font-black text-white text-xs shadow-md">
          D
        </div>
        <div>
          <div class="text-[10px] text-gray-400 font-bold uppercase tracking-wider">DRIVEE • MUMBAI</div>
          <button id="locationBtn" onclick="openLocationModal()" class="flex items-center text-xs font-bold text-white hover:text-drivee-orange transition-colors">
            <span id="currentLocationText">Bandra West, Mumbai</span>
            <svg class="w-3.5 h-3.5 ml-1 text-drivee-orange" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg>
          </button>
        </div>
      </div>

      <div class="flex items-center space-x-2">
        <!-- SOS Quick Button -->
        <button onclick="openSosModal()" class="flex items-center space-x-1 bg-red-600/20 border border-red-500/40 text-red-400 px-2.5 py-1 rounded-full text-[11px] font-bold hover:bg-red-600 hover:text-white transition-all shadow-sm">
          <svg class="w-3.5 h-3.5 text-red-500" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"/></svg>
          <span>SOS</span>
        </button>

        <!-- Native APK Badge -->
        <a href="/download-apk" title="Download Compiled Android APK" class="bg-drivee-surfaceHigh border border-drivee-border text-gray-200 p-1.5 rounded-full hover:text-drivee-orange transition-colors">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"></path></svg>
        </a>
      </div>
    </header>

    <!-- Scrollable Screen Content -->
    <main id="screenContainer" class="flex-1 overflow-y-auto custom-scroll pb-20">
      
      <!-- SCREEN 1: HOME & BOOKING -->
      <section id="screenHome" class="block">
        <!-- Hero Value Prop -->
        <div class="bg-gradient-to-b from-drivee-navy to-drivee-bg p-5 border-b border-drivee-border">
          <div class="inline-flex items-center space-x-1.5 bg-drivee-orange/15 border border-drivee-orange/40 text-drivee-orange text-[10px] font-extrabold px-2.5 py-0.5 rounded-full mb-2">
            <span>🛡️ NOT A TAXI • WE DRIVE YOUR CAR</span>
          </div>
          <h1 class="text-xl font-extrabold text-white leading-tight">
            Personal Chauffeurs For Your Personal Vehicle
          </h1>
          <p class="text-xs text-gray-300 mt-1.5 leading-relaxed">
            Screened, police-verified chauffeurs on demand in Mumbai. Zero surge pricing. ₹50 Lakh vehicle protection shield included.
          </p>

          <!-- Badges -->
          <div class="grid grid-cols-3 gap-2 mt-4">
            <div class="bg-drivee-surface p-2 rounded-xl border border-drivee-border text-center">
              <div class="text-[14px] font-black text-white">₹149<span class="text-[10px] text-drivee-orange">/hr</span></div>
              <div class="text-[9px] text-gray-400 mt-0.5">Flat Pricing</div>
            </div>
            <div class="bg-drivee-surface p-2 rounded-xl border border-drivee-border text-center">
              <div class="text-[14px] font-black text-drivee-green">₹50L</div>
              <div class="text-[9px] text-gray-400 mt-0.5">ICICI Shield</div>
            </div>
            <div class="bg-drivee-surface p-2 rounded-xl border border-drivee-border text-center">
              <div class="text-[14px] font-black text-white">12 <span class="text-[10px] text-drivee-orange">min</span></div>
              <div class="text-[9px] text-gray-400 mt-0.5">Avg Arrival</div>
            </div>
          </div>
        </div>

        <!-- Booking Calculator Card -->
        <div class="p-4">
          <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-xl">
            <div class="flex items-center justify-between mb-3">
              <span class="text-xs font-black text-gray-300 tracking-wide uppercase">RESERVE A CHAUFFEUR</span>
              <span class="text-[10px] bg-emerald-500/20 text-emerald-400 font-bold px-2 py-0.5 rounded">ONLINE INSTANT</span>
            </div>

            <!-- Transmission Selector -->
            <div class="mb-3">
              <label class="text-[11px] font-semibold text-gray-400 block mb-1.5">Your Car's Transmission:</label>
              <div class="grid grid-cols-2 gap-2">
                <button type="button" id="transManual" onclick="setTransmission('manual')" class="py-2 text-xs font-bold rounded-xl border bg-drivee-orange border-drivee-orange text-white flex items-center justify-center space-x-1">
                  <span>⚙️ Manual Stick</span>
                </button>
                <button type="button" id="transAuto" onclick="setTransmission('auto')" class="py-2 text-xs font-bold rounded-xl border bg-drivee-surfaceLow border-drivee-border text-gray-400 flex items-center justify-center space-x-1">
                  <span>⚡ Automatic (AT/DCT)</span>
                </button>
              </div>
            </div>

            <!-- Duration Chips -->
            <div class="mb-4">
              <label class="text-[11px] font-semibold text-gray-400 block mb-1.5">Required Booking Hours:</label>
              <div class="grid grid-cols-5 gap-1.5 text-center">
                <button onclick="setHours(2)" id="h2" class="py-2 rounded-lg border border-drivee-border bg-drivee-surfaceLow text-xs font-bold text-gray-300 hover:border-drivee-orange">2 hrs</button>
                <button onclick="setHours(3)" id="h3" class="py-2 rounded-lg border border-drivee-border bg-drivee-surfaceLow text-xs font-bold text-gray-300 hover:border-drivee-orange">3 hrs</button>
                <button onclick="setHours(4)" id="h4" class="py-2 rounded-lg border-2 border-drivee-orange bg-drivee-orange/20 text-xs font-black text-white">4 hrs</button>
                <button onclick="setHours(6)" id="h6" class="py-2 rounded-lg border border-drivee-border bg-drivee-surfaceLow text-xs font-bold text-gray-300 hover:border-drivee-orange">6 hrs</button>
                <button onclick="setHours(8)" id="h8" class="py-2 rounded-lg border border-drivee-border bg-drivee-surfaceLow text-xs font-bold text-gray-300 hover:border-drivee-orange">8 hrs</button>
              </div>
            </div>

            <!-- Total Price Summary -->
            <div class="bg-drivee-surfaceLow p-3 rounded-xl border border-drivee-border flex items-center justify-between mb-4">
              <div>
                <div class="text-[11px] text-gray-400">Total Estimated Fare:</div>
                <div class="text-xl font-black text-drivee-orange" id="bookingTotalText">₹596.00</div>
              </div>
              <div class="text-right">
                <div class="text-[10px] text-drivee-green font-bold">✓ Zero Surge Ever</div>
                <div class="text-[9px] text-gray-400">₹149/hr • Incl. Insurance</div>
              </div>
            </div>

            <!-- Action Button -->
            <button onclick="navTo('screenDrivers')" class="w-full py-3.5 bg-drivee-orange hover:bg-drivee-orangeHover text-white font-extrabold rounded-xl shadow-lg transition-all flex items-center justify-center space-x-2">
              <span>View Available Chauffeurs</span>
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path></svg>
            </button>
          </div>
        </div>

        <!-- Chauffeur Availability Pulse -->
        <div class="px-4 mb-4">
          <div class="bg-drivee-navy/80 rounded-2xl p-3.5 border border-drivee-border flex items-center justify-between">
            <div class="flex items-center space-x-3">
              <div class="relative">
                <div class="w-10 h-10 rounded-full bg-drivee-orange/20 flex items-center justify-center border border-drivee-orange">
                  <span class="text-sm">📍</span>
                </div>
                <div class="absolute -inset-1 rounded-full border border-drivee-orange pulse-ring"></div>
              </div>
              <div>
                <div class="text-xs font-bold text-white">4 Chauffeurs near Bandra West</div>
                <div class="text-[10px] text-emerald-400 font-semibold">Fastest arrival in ~12 mins</div>
              </div>
            </div>
            <button onclick="navTo('screenDrivers')" class="text-xs font-bold text-drivee-orange hover:underline">
              Browse →
            </button>
          </div>
        </div>

        <!-- Popular Use Cases -->
        <div class="px-4 mb-5">
          <h2 class="text-xs font-bold text-gray-400 uppercase tracking-wider mb-2.5">POPULAR USE CASES</h2>
          <div class="grid grid-cols-2 gap-2.5">
            <div class="bg-drivee-surface p-3 rounded-xl border border-drivee-border">
              <div class="text-base mb-1">🥂</div>
              <div class="text-xs font-bold text-white">Late Night & Events</div>
              <div class="text-[10px] text-gray-400 mt-0.5">Celebrate freely. Get driven back safely in your car.</div>
            </div>
            <div class="bg-drivee-surface p-3 rounded-xl border border-drivee-border">
              <div class="text-base mb-1">🏥</div>
              <div class="text-xs font-bold text-white">Hospital & Elderly Care</div>
              <div class="text-[10px] text-gray-400 mt-0.5">Doorstep escort, basement parking, patience.</div>
            </div>
            <div class="bg-drivee-surface p-3 rounded-xl border border-drivee-border">
              <div class="text-base mb-1">✈️</div>
              <div class="text-xs font-bold text-white">Airport Drop / Pickup</div>
              <div class="text-[10px] text-gray-400 mt-0.5">Bring your car back home safely without airport parking costs.</div>
            </div>
            <div class="bg-drivee-surface p-3 rounded-xl border border-drivee-border">
              <div class="text-base mb-1">💼</div>
              <div class="text-xs font-bold text-white">BKC Corporate Day</div>
              <div class="text-[10px] text-gray-400 mt-0.5">Work from the backseat while our driver handles traffic.</div>
            </div>
          </div>
        </div>

      </section>

      <!-- SCREEN 2: SCREENED CHAUFFEURS LIST -->
      <section id="screenDrivers" class="hidden p-4 space-y-3">
        <div class="flex items-center justify-between pb-2 border-b border-drivee-border">
          <div>
            <h2 class="text-sm font-extrabold text-white">AVAILABLE CHAUFFEURS</h2>
            <p class="text-[10px] text-gray-400">Bandra West • <span id="driverHoursText">4 Hours</span> Booking</p>
          </div>
          <button onclick="navTo('screenHome')" class="text-xs text-drivee-orange font-bold">Edit</button>
        </div>

        <!-- Driver 1 -->
        <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-md space-y-3">
          <div class="flex items-start justify-between">
            <div class="flex items-center space-x-3">
              <img src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80" class="w-12 h-12 rounded-full border-2 border-drivee-orange object-cover" alt="Rajesh Kumar">
              <div>
                <div class="flex items-center space-x-1.5">
                  <h3 class="text-sm font-bold text-white">Rajesh Kumar</h3>
                  <span class="text-emerald-400 text-xs">✓ Verified</span>
                </div>
                <div class="text-[11px] text-gray-400">34 yrs • 128 trips • Luxury Sedans</div>
                <div class="flex items-center space-x-1 mt-0.5 text-xs">
                  <span class="text-amber-400 font-bold">★ 4.90</span>
                  <span class="text-gray-500">•</span>
                  <span class="text-gray-400 text-[10px]">Police Clearance Clear</span>
                </div>
              </div>
            </div>
            <div class="text-right">
              <div class="text-base font-black text-drivee-orange">₹149<span class="text-[10px]">/hr</span></div>
              <div class="text-[10px] text-emerald-400 font-semibold">15m away</div>
            </div>
          </div>
          <div class="bg-drivee-surfaceLow p-2.5 rounded-xl border border-drivee-border text-[10px] text-gray-300">
            Skills: Honda City, BMW 3, Creta, Fortuner • Etiquette Certified • Non-Smoker
          </div>
          <button onclick="selectDriver('Rajesh Kumar', '15m')" class="w-full py-2.5 bg-drivee-orange hover:bg-drivee-orangeHover font-bold text-white text-xs rounded-xl transition-all shadow">
            Book Rajesh Now (₹596)
          </button>
        </div>

        <!-- Driver 2 -->
        <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-md space-y-3">
          <div class="flex items-start justify-between">
            <div class="flex items-center space-x-3">
              <img src="https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&auto=format&fit=crop&q=80" class="w-12 h-12 rounded-full border-2 border-gray-600 object-cover" alt="Suresh Patil">
              <div>
                <div class="flex items-center space-x-1.5">
                  <h3 class="text-sm font-bold text-white">Suresh Patil</h3>
                  <span class="text-emerald-400 text-xs">✓ Verified</span>
                </div>
                <div class="text-[11px] text-gray-400">41 yrs • 240 trips • SUV & Highway Specialist</div>
                <div class="flex items-center space-x-1 mt-0.5 text-xs">
                  <span class="text-amber-400 font-bold">★ 4.95</span>
                  <span class="text-gray-500">•</span>
                  <span class="text-gray-400 text-[10px]">15-Yr Clean Driving Record</span>
                </div>
              </div>
            </div>
            <div class="text-right">
              <div class="text-base font-black text-drivee-orange">₹169<span class="text-[10px]">/hr</span></div>
              <div class="text-[10px] text-amber-400 font-semibold">25m away</div>
            </div>
          </div>
          <div class="bg-drivee-surfaceLow p-2.5 rounded-xl border border-drivee-border text-[10px] text-gray-300">
            Skills: Heavy SUVs, Expressways, Night Driving • Senior Driver Specialist
          </div>
          <button onclick="selectDriver('Suresh Patil', '25m')" class="w-full py-2.5 bg-drivee-navy border border-drivee-border hover:bg-drivee-surfaceHigh font-bold text-white text-xs rounded-xl transition-all shadow">
            Book Suresh Now (₹676)
          </button>
        </div>

        <!-- Driver 3 -->
        <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-md space-y-3">
          <div class="flex items-start justify-between">
            <div class="flex items-center space-x-3">
              <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&auto=format&fit=crop&q=80" class="w-12 h-12 rounded-full border-2 border-gray-600 object-cover" alt="Mohammad Imran">
              <div>
                <div class="flex items-center space-x-1.5">
                  <h3 class="text-sm font-bold text-white">Mohammad Imran</h3>
                  <span class="text-emerald-400 text-xs">✓ Verified</span>
                </div>
                <div class="text-[11px] text-gray-400">29 yrs • 92 trips • Automatic & City Traffic</div>
                <div class="flex items-center space-x-1 mt-0.5 text-xs">
                  <span class="text-amber-400 font-bold">★ 4.85</span>
                  <span class="text-gray-500">•</span>
                  <span class="text-gray-400 text-[10px]">Family Escort Trained</span>
                </div>
              </div>
            </div>
            <div class="text-right">
              <div class="text-base font-black text-drivee-orange">₹149<span class="text-[10px]">/hr</span></div>
              <div class="text-[10px] text-emerald-400 font-semibold">20m away</div>
            </div>
          </div>
          <div class="bg-drivee-surfaceLow p-2.5 rounded-xl border border-drivee-border text-[10px] text-gray-300">
            Skills: City automatics, tight parking, elderly patient assistance
          </div>
          <button onclick="selectDriver('Mohammad Imran', '20m')" class="w-full py-2.5 bg-drivee-navy border border-drivee-border hover:bg-drivee-surfaceHigh font-bold text-white text-xs rounded-xl transition-all shadow">
            Book Mohammad Now (₹596)
          </button>
        </div>
      </section>

      <!-- SCREEN 3: LIVE GPS TRACKING -->
      <section id="screenTracking" class="hidden">
        <!-- Live Status Bar -->
        <div class="bg-drivee-navyDark p-4 border-b border-drivee-border">
          <div class="flex items-center justify-between text-[11px]">
            <div class="flex items-center space-x-1.5">
              <span class="w-2 h-2 rounded-full bg-emerald-500 animate-ping"></span>
              <span class="font-black text-emerald-400 uppercase tracking-wide">TRIP IN PROGRESS • #DRV-8824</span>
            </div>
            <span class="bg-drivee-orange px-2 py-0.5 rounded text-[9px] font-extrabold text-white">OTP VERIFIED</span>
          </div>
          <div class="text-sm font-bold text-white mt-1">Bandra West → Worli Sea Link</div>

          <div class="grid grid-cols-3 gap-2 mt-3">
            <div class="bg-white/5 p-2 rounded-lg text-center">
              <div class="text-[9px] text-gray-400">Elapsed</div>
              <div class="text-xs font-black text-white">1h 18m</div>
            </div>
            <div class="bg-white/5 p-2 rounded-lg text-center">
              <div class="text-[9px] text-gray-400">Remaining</div>
              <div class="text-xs font-black text-drivee-orange" id="trackingRemainingText">2h 42m</div>
            </div>
            <div class="bg-white/5 p-2 rounded-lg text-center">
              <div class="text-[9px] text-gray-400">Booked</div>
              <div class="text-xs font-black text-white" id="trackingTotalText">4 Hours</div>
            </div>
          </div>
        </div>

        <!-- Simulated Map View Canvas -->
        <div class="relative w-full h-56 bg-slate-900 border-b border-drivee-border overflow-hidden">
          <canvas id="gpsCanvas" class="w-full h-full"></canvas>
          
          <!-- Live Speedometer Badge -->
          <div class="absolute top-3 left-3 bg-drivee-navyDark/90 border border-slate-700 px-2.5 py-1 rounded-lg text-xs font-bold text-white flex items-center space-x-1.5 shadow-lg">
            <span class="text-drivee-orange">⚡</span>
            <span id="speedValue">42 km/h</span>
            <span class="text-[10px] text-emerald-400">• Live GPS</span>
          </div>

          <!-- Toll ETA Badge -->
          <div class="absolute top-3 right-3 bg-drivee-navyDark/90 border border-slate-700 px-2.5 py-1 rounded-lg text-[11px] font-semibold text-white shadow-lg">
            Sea Link Toll: 18 min
          </div>
        </div>

        <!-- Assigned Chauffeur Details -->
        <div class="p-4 space-y-3">
          <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-md">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-3">
                <img id="activeDriverImg" src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80" class="w-12 h-12 rounded-full border-2 border-drivee-orange object-cover" alt="Driver">
                <div>
                  <div class="flex items-center space-x-1.5">
                    <h4 class="text-sm font-bold text-white" id="activeDriverName">Rajesh Kumar</h4>
                    <span class="text-emerald-400 text-xs">✓</span>
                  </div>
                  <div class="text-[11px] text-gray-400">Assigned Personal Chauffeur</div>
                  <div class="text-[10px] text-amber-400 font-bold mt-0.5">★ 4.90 • 128 trips</div>
                </div>
              </div>

              <!-- Call & Chat -->
              <div class="flex space-x-2">
                <button onclick="alert('Calling driver...')" class="w-9 h-9 rounded-full bg-emerald-600/20 text-emerald-400 flex items-center justify-center border border-emerald-500/30 hover:bg-emerald-600 hover:text-white transition-all">
                  📞
                </button>
                <button onclick="alert('Opening in-app driver chat...')" class="w-9 h-9 rounded-full bg-drivee-surfaceLow text-gray-300 flex items-center justify-center border border-drivee-border hover:bg-drivee-surfaceHigh transition-all">
                  💬
                </button>
              </div>
            </div>

            <div class="border-t border-drivee-border mt-3 pt-3 flex items-center justify-between text-xs">
              <div class="flex items-center space-x-1.5 text-gray-300">
                <span>🚗</span>
                <span>Piloting: <strong>Honda City i-VTEC (MH 02 CZ 4410)</strong></span>
              </div>
              <span class="text-[9px] bg-emerald-500/20 text-emerald-400 px-2 py-0.5 rounded font-bold">PROTECTED</span>
            </div>
          </div>

          <!-- 1-Tap Booking Extension -->
          <div class="bg-drivee-surface rounded-2xl border border-drivee-border p-4 shadow-md">
            <div class="flex items-center justify-between mb-2">
              <span class="text-xs font-black text-gray-200 uppercase">EXTEND YOUR BOOKING</span>
              <span id="extensionBadge" class="text-[10px] text-emerald-400 font-bold hidden">+1 hr added</span>
            </div>
            <p class="text-[11px] text-gray-400 mb-3">Plans changed? Extend in 1 tap without interrupting your driver.</p>
            <div class="grid grid-cols-2 gap-2">
              <button onclick="extendTrip(1)" class="py-2.5 bg-drivee-surfaceLow hover:bg-drivee-surfaceHigh border border-drivee-border text-xs font-bold text-white rounded-xl transition-all">
                +1 Hour (₹149)
              </button>
              <button onclick="extendTrip(2)" class="py-2.5 bg-drivee-orange hover:bg-drivee-orangeHover text-xs font-bold text-white rounded-xl transition-all shadow">
                +2 Hours (₹298)
              </button>
            </div>
          </div>

          <!-- WhatsApp Share & SOS Actions -->
          <div class="grid grid-cols-2 gap-2">
            <button onclick="alert('Live tracking WhatsApp share link copied to clipboard!')" class="py-2.5 bg-emerald-600/15 border border-emerald-500/40 text-emerald-400 text-xs font-bold rounded-xl hover:bg-emerald-600 hover:text-white transition-all flex items-center justify-center space-x-1.5">
              <span>📲 Share on WhatsApp</span>
            </button>
            <button onclick="openSosModal()" class="py-2.5 bg-red-600 hover:bg-red-700 text-white text-xs font-bold rounded-xl transition-all flex items-center justify-center space-x-1.5 shadow-lg">
              <span>🚨 24x7 Safety SOS</span>
            </button>
          </div>

          <!-- Complete Ride Button -->
          <button onclick="openRatingModal()" class="w-full py-3 bg-drivee-surfaceHigh hover:bg-drivee-border text-white text-xs font-bold rounded-xl border border-drivee-border transition-all">
            End Trip & View Invoice
          </button>
        </div>
      </section>

      <!-- SCREEN 4: FAMILY MODE -->
      <section id="screenFamily" class="hidden p-4 space-y-4">
        <div class="bg-gradient-to-r from-drivee-navy to-drivee-surface p-4 rounded-2xl border border-drivee-border">
          <div class="text-[10px] text-drivee-orange font-black uppercase tracking-wider">CARE FOR YOUR LOVED ONES</div>
          <h2 class="text-base font-extrabold text-white mt-1">Family Mode Chauffeur Service</h2>
          <p class="text-xs text-gray-300 mt-1">
            Real-time WhatsApp GPS broadcasts, door-to-door escort, and hospital basement parking escort for elderly parents.
          </p>
        </div>

        <!-- Family Profiles -->
        <div class="space-y-3">
          <div class="bg-drivee-surface p-4 rounded-2xl border border-drivee-border">
            <div class="flex items-start justify-between">
              <div>
                <h3 class="text-sm font-bold text-white">Dad (Ramesh Mehta, 68)</h3>
                <p class="text-xs text-gray-400 mt-0.5">Route: Bandra West → Lilavati Hospital OPD</p>
                <div class="text-[10px] text-emerald-400 mt-1 font-semibold">Special Instructions: Hospital basement wheelchair lobby escort.</div>
              </div>
              <span class="text-xs bg-emerald-500/20 text-emerald-400 px-2 py-0.5 rounded font-bold">SENIOR CARE</span>
            </div>
            <button onclick="navTo('screenTracking')" class="w-full mt-3 py-2 bg-drivee-orange text-white text-xs font-bold rounded-xl">
              Dispatch Driver for Dad (Creta AT)
            </button>
          </div>

          <div class="bg-drivee-surface p-4 rounded-2xl border border-drivee-border">
            <div class="flex items-start justify-between">
              <div>
                <h3 class="text-sm font-bold text-white">Mom (Sunita Mehta, 64)</h3>
                <p class="text-xs text-gray-400 mt-0.5">Route: Bandra West → Siddhivinayak Temple & Market</p>
                <div class="text-[10px] text-gray-300 mt-1">Instructions: Driver holds car in temple waiting bay with AC.</div>
              </div>
              <span class="text-xs bg-drivee-surfaceHigh text-gray-300 px-2 py-0.5 rounded font-bold">SAVED ROUTE</span>
            </div>
            <button onclick="navTo('screenTracking')" class="w-full mt-3 py-2 bg-drivee-navy border border-drivee-border text-white text-xs font-bold rounded-xl">
              Dispatch Driver for Mom
            </button>
          </div>
        </div>
      </section>

      <!-- SCREEN 5: BILLING & ACCOUNT -->
      <section id="screenAccount" class="hidden p-4 space-y-4">
        <!-- User Profile Card -->
        <div class="bg-drivee-surface p-4 rounded-2xl border border-drivee-border flex items-center space-x-3">
          <img src="https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80" class="w-14 h-14 rounded-full border-2 border-drivee-orange object-cover" alt="Anshul Mehta">
          <div>
            <div class="flex items-center space-x-1.5">
              <h3 class="text-sm font-bold text-white">Anshul Mehta</h3>
              <span class="text-[9px] bg-drivee-orange px-1.5 py-0.5 rounded font-black text-white">GOLD</span>
            </div>
            <div class="text-xs text-gray-400 mt-0.5">+91 98201 44521 • Bandra West</div>
            <div class="text-[10px] text-gray-500">Honda City Petrol Manual (MH 02 FJ 4410)</div>
          </div>
        </div>

        <!-- Bento Metrics Grid -->
        <div class="grid grid-cols-4 gap-1.5 text-center">
          <div class="bg-drivee-surface p-2.5 rounded-xl border border-drivee-border">
            <div class="text-sm font-black text-white">38.5</div>
            <div class="text-[9px] text-gray-400">Chauffeur Hrs</div>
          </div>
          <div class="bg-drivee-surface p-2.5 rounded-xl border border-drivee-border">
            <div class="text-sm font-black text-drivee-orange">₹5,736</div>
            <div class="text-[9px] text-gray-400">Total Spend</div>
          </div>
          <div class="bg-drivee-surface p-2.5 rounded-xl border border-drivee-border">
            <div class="text-sm font-black text-amber-400">4.95★</div>
            <div class="text-[9px] text-gray-400">Avg Rating</div>
          </div>
          <div class="bg-drivee-surface p-2.5 rounded-xl border border-drivee-border">
            <div class="text-sm font-black text-emerald-400">₹50L</div>
            <div class="text-[9px] text-gray-400">Shield Cover</div>
          </div>
        </div>

        <!-- Billing & GST Center -->
        <div class="bg-drivee-surface p-4 rounded-2xl border border-drivee-border space-y-2.5">
          <div class="flex items-center justify-between">
            <span class="text-xs font-black text-gray-200 uppercase">GST TAX INVOICE CENTER</span>
            <span class="text-[9px] text-emerald-400 font-bold">ITC ELIGIBLE</span>
          </div>
          <p class="text-xs text-gray-400 leading-relaxed">
            Registered GSTIN: <strong>27AABCT3518Q1Z4</strong> (Mehta Global Advisory LLP). Rule 46 compliant tax invoices generated automatically.
          </p>
          <button onclick="openInvoiceModal()" class="w-full py-2.5 bg-drivee-surfaceLow border border-drivee-border text-white text-xs font-bold rounded-xl hover:bg-drivee-surfaceHigh transition-all">
            View Latest GST Tax Invoice (#DRV-8824)
          </button>
        </div>

        <!-- Native APK Card -->
        <div class="bg-drivee-navyDark p-4 rounded-2xl border border-drivee-border flex items-center justify-between">
          <div>
            <div class="text-xs font-bold text-white">Native Android App (APK)</div>
            <div class="text-[10px] text-gray-400">Jetpack Compose • 36.0.0 SDK build</div>
          </div>
          <a href="/download-apk" class="px-3 py-1.5 bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs rounded-xl shadow">
            Download APK
          </a>
        </div>
      </section>

    </main>

    <!-- Bottom Navigation Bar -->
    <nav class="bg-drivee-navy border-t border-drivee-border px-2 py-2 flex items-center justify-around absolute bottom-0 left-0 right-0 z-30">
      <button onclick="navTo('screenHome')" id="navHome" class="flex flex-col items-center text-drivee-orange font-bold text-[10px] transition-colors">
        <span class="text-lg">🏠</span>
        <span>Book</span>
      </button>
      <button onclick="navTo('screenDrivers')" id="navDrivers" class="flex flex-col items-center text-gray-400 hover:text-white font-bold text-[10px] transition-colors">
        <span class="text-lg">🚗</span>
        <span>Drivers</span>
      </button>
      <button onclick="navTo('screenTracking')" id="navTracking" class="flex flex-col items-center text-gray-400 hover:text-white font-bold text-[10px] transition-colors relative">
        <span class="text-lg">📍</span>
        <span>Live Trip</span>
        <span class="w-2 h-2 rounded-full bg-emerald-400 absolute top-0 right-2 animate-ping"></span>
      </button>
      <button onclick="navTo('screenFamily')" id="navFamily" class="flex flex-col items-center text-gray-400 hover:text-white font-bold text-[10px] transition-colors">
        <span class="text-lg">👨‍👩‍👦</span>
        <span>Family</span>
      </button>
      <button onclick="navTo('screenAccount')" id="navAccount" class="flex flex-col items-center text-gray-400 hover:text-white font-bold text-[10px] transition-colors">
        <span class="text-lg">👤</span>
        <span>Account</span>
      </button>
    </nav>

    <!-- MODAL: Location Picker -->
    <div id="locationModal" class="hidden fixed inset-0 bg-black/70 backdrop-blur-sm z-50 flex items-end md:items-center justify-center p-0 md:p-4">
      <div class="bg-drivee-surface w-full max-w-md rounded-t-3xl md:rounded-3xl p-5 border-t md:border border-drivee-border">
        <div class="flex items-center justify-between pb-3 border-b border-drivee-border">
          <h3 class="text-sm font-extrabold text-white">SELECT MUMBAI PICKUP LOCATION</h3>
          <button onclick="closeLocationModal()" class="text-gray-400 text-lg">✕</button>
        </div>
        <div class="py-2 space-y-1">
          <button onclick="selectLoc('Bandra West, Mumbai')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-white flex items-center justify-between">
            <span>📍 Bandra West, Mumbai</span>
            <span class="text-drivee-orange text-xs">✓ Active</span>
          </button>
          <button onclick="selectLoc('Juhu / Santacruz, Mumbai')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-gray-300">
            📍 Juhu / Santacruz, Mumbai
          </button>
          <button onclick="selectLoc('Worli Sea Face, Mumbai')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-gray-300">
            📍 Worli Sea Face, Mumbai
          </button>
          <button onclick="selectLoc('Bandra Kurla Complex (BKC)')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-gray-300">
            📍 Bandra Kurla Complex (BKC)
          </button>
          <button onclick="selectLoc('Powai / Hiranandani')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-gray-300">
            📍 Powai / Hiranandani
          </button>
          <button onclick="selectLoc('Colaba / Nariman Point')" class="w-full text-left py-2.5 px-3 rounded-xl hover:bg-drivee-surfaceLow text-xs font-bold text-gray-300">
            📍 Colaba / Nariman Point
          </button>
        </div>
      </div>
    </div>

    <!-- MODAL: Emergency SOS -->
    <div id="sosModal" class="hidden fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
      <div class="bg-drivee-surface w-full max-w-sm rounded-3xl p-6 border border-red-500/50 shadow-2xl text-center">
        <div class="w-16 h-16 rounded-full bg-red-600/20 text-red-500 border border-red-500 mx-auto flex items-center justify-center text-2xl mb-3">
          🚨
        </div>
        <h3 class="text-base font-black text-white">DRIVEE 24x7 EMERGENCY SOS</h3>
        <p class="text-xs text-gray-300 mt-1 leading-relaxed">
          Immediate priority escalation. Live GPS telemetry is broadcasting to DRIVEE Safety Ops and Mumbai Police Emergency Control (112).
        </p>
        <div class="my-4 p-3 bg-drivee-surfaceLow rounded-xl border border-drivee-border text-left text-xs space-y-1">
          <div class="text-gray-400">Current Trip: <strong class="text-white">#DRV-8824</strong></div>
          <div class="text-gray-400">Driver: <strong class="text-white">Rajesh Kumar</strong></div>
          <div class="text-gray-400">Car: <strong class="text-white">Honda City (MH 02 CZ 4410)</strong></div>
        </div>
        <div class="space-y-2">
          <button onclick="alert('Calling Mumbai Police Control (112)...')" class="w-full py-3 bg-red-600 text-white font-black text-xs rounded-xl shadow-lg hover:bg-red-700">
            Call Police Control Room (112)
          </button>
          <button onclick="alert('Connecting to 24x7 DRIVEE Safety Desk...')" class="w-full py-2.5 bg-drivee-navy text-gray-300 font-bold text-xs rounded-xl border border-drivee-border hover:text-white">
            Call DRIVEE Safety Incident Manager
          </button>
          <button onclick="closeSosModal()" class="w-full py-2 text-gray-400 text-xs font-semibold">
            Cancel / False Alarm
          </button>
        </div>
      </div>
    </div>

    <!-- MODAL: GST Tax Invoice -->
    <div id="invoiceModal" class="hidden fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
      <div class="bg-drivee-surface w-full max-w-sm rounded-3xl p-5 border border-drivee-border shadow-2xl text-xs space-y-3">
        <div class="flex items-center justify-between pb-2 border-b border-drivee-border">
          <div>
            <div class="text-[9px] text-gray-400 uppercase font-black">TAX INVOICE (RULE 46)</div>
            <div class="text-sm font-black text-white">#DRV-INV-2024-8824</div>
          </div>
          <button onclick="closeInvoiceModal()" class="text-gray-400 text-base">✕</button>
        </div>

        <div class="bg-drivee-surfaceLow p-2.5 rounded-xl text-[10px] space-y-1 text-gray-300">
          <div>Billed To: <strong class="text-white">Mehta Global Advisory LLP</strong></div>
          <div>GSTIN: <strong class="text-white">27AABCT3518Q1Z4</strong> (Maharashtra)</div>
          <div>SAC Code: <strong class="text-white">9966 (Passenger Transport Service)</strong></div>
        </div>

        <div class="space-y-1.5 text-gray-300 text-[11px]">
          <div class="flex justify-between">
            <span>Base Chauffeur Service (4 hrs @ ₹149):</span>
            <span class="font-bold text-white">₹596.00</span>
          </div>
          <div class="flex justify-between">
            <span>CGST (9.0%):</span>
            <span>₹53.64</span>
          </div>
          <div class="flex justify-between">
            <span>SGST (9.0%):</span>
            <span>₹53.64</span>
          </div>
          <div class="flex justify-between text-emerald-400">
            <span>₹50L ICICI Shield Coverage:</span>
            <span>Included (₹0)</span>
          </div>
          <div class="border-t border-drivee-border pt-1.5 flex justify-between font-black text-sm text-drivee-orange">
            <span>Total Amount Paid:</span>
            <span>₹703.28</span>
          </div>
        </div>

        <div class="pt-2">
          <button onclick="alert('Tax Invoice PDF downloaded to your files.')" class="w-full py-2.5 bg-drivee-orange text-white font-bold text-xs rounded-xl shadow">
            Download Signed PDF Invoice
          </button>
        </div>
      </div>
    </div>

    <!-- MODAL: Trip Complete & Rating -->
    <div id="ratingModal" class="hidden fixed inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
      <div class="bg-drivee-surface w-full max-w-sm rounded-3xl p-6 border border-drivee-border shadow-2xl text-center space-y-3">
        <div class="text-3xl">🎉</div>
        <h3 class="text-base font-black text-white">TRIP COMPLETED</h3>
        <p class="text-xs text-gray-300">How was your chauffeur experience with Rajesh Kumar?</p>
        <div class="flex justify-center space-x-2 text-2xl text-amber-400 py-2">
          <button onclick="this.classList.toggle('opacity-50')">★</button>
          <button onclick="this.classList.toggle('opacity-50')">★</button>
          <button onclick="this.classList.toggle('opacity-50')">★</button>
          <button onclick="this.classList.toggle('opacity-50')">★</button>
          <button onclick="this.classList.toggle('opacity-50')">★</button>
        </div>
        <button onclick="finishTrip()" class="w-full py-2.5 bg-drivee-orange text-white font-bold text-xs rounded-xl">
          Submit & View Tax Invoice
        </button>
      </div>
    </div>

  </div>

  <script>
    let bookedHours = 4;
    let extendedHours = 0;
    let transmission = 'manual';

    function setTransmission(type) {
      transmission = type;
      const m = document.getElementById('transManual');
      const a = document.getElementById('transAuto');
      if (type === 'manual') {
        m.className = 'py-2 text-xs font-bold rounded-xl border bg-drivee-orange border-drivee-orange text-white flex items-center justify-center space-x-1';
        a.className = 'py-2 text-xs font-bold rounded-xl border bg-drivee-surfaceLow border-drivee-border text-gray-400 flex items-center justify-center space-x-1';
      } else {
        a.className = 'py-2 text-xs font-bold rounded-xl border bg-drivee-orange border-drivee-orange text-white flex items-center justify-center space-x-1';
        m.className = 'py-2 text-xs font-bold rounded-xl border bg-drivee-surfaceLow border-drivee-border text-gray-400 flex items-center justify-center space-x-1';
      }
    }

    function setHours(h) {
      bookedHours = h;
      [2, 3, 4, 6, 8].forEach(i => {
        const el = document.getElementById('h' + i);
        if (i === h) {
          el.className = 'py-2 rounded-lg border-2 border-drivee-orange bg-drivee-orange/20 text-xs font-black text-white';
        } else {
          el.className = 'py-2 rounded-lg border border-drivee-border bg-drivee-surfaceLow text-xs font-bold text-gray-300 hover:border-drivee-orange';
        }
      });
      document.getElementById('bookingTotalText').innerText = '₹' + (h * 149) + '.00';
      document.getElementById('driverHoursText').innerText = h + ' Hours';
    }

    function navTo(screenId) {
      ['screenHome', 'screenDrivers', 'screenTracking', 'screenFamily', 'screenAccount'].forEach(id => {
        document.getElementById(id).classList.add('hidden');
      });
      document.getElementById(screenId).classList.remove('hidden');

      // Update Nav bar icons
      const navMap = {
        screenHome: 'navHome',
        screenDrivers: 'navDrivers',
        screenTracking: 'navTracking',
        screenFamily: 'navFamily',
        screenAccount: 'navAccount'
      };
      Object.values(navMap).forEach(btnId => {
        const btn = document.getElementById(btnId);
        btn.classList.remove('text-drivee-orange');
        btn.classList.add('text-gray-400');
      });
      const activeBtn = document.getElementById(navMap[screenId]);
      if (activeBtn) {
        activeBtn.classList.remove('text-gray-400');
        activeBtn.classList.add('text-drivee-orange');
      }

      if (screenId === 'screenTracking') {
        initGpsCanvas();
      }
    }

    function selectDriver(name, eta) {
      document.getElementById('activeDriverName').innerText = name;
      navTo('screenTracking');
    }

    function extendTrip(extraH) {
      extendedHours += extraH;
      const total = bookedHours + extendedHours;
      document.getElementById('trackingTotalText').innerText = total + ' Hours';
      document.getElementById('trackingRemainingText').innerText = (total - 1) + 'h 42m';
      const badge = document.getElementById('extensionBadge');
      badge.innerText = '+' + extendedHours + ' hrs added';
      badge.classList.remove('hidden');
      alert('Added +' + extraH + ' Hour(s)! Driver has been updated on the route.');
    }

    function openLocationModal() { document.getElementById('locationModal').classList.remove('hidden'); }
    function closeLocationModal() { document.getElementById('locationModal').classList.add('hidden'); }
    function selectLoc(loc) {
      document.getElementById('currentLocationText').innerText = loc;
      closeLocationModal();
    }

    function openSosModal() { document.getElementById('sosModal').classList.remove('hidden'); }
    function closeSosModal() { document.getElementById('sosModal').classList.add('hidden'); }

    function openInvoiceModal() { document.getElementById('invoiceModal').classList.remove('hidden'); }
    function closeInvoiceModal() { document.getElementById('invoiceModal').classList.add('hidden'); }

    function openRatingModal() { document.getElementById('ratingModal').classList.remove('hidden'); }
    function finishTrip() {
      document.getElementById('ratingModal').classList.add('hidden');
      openInvoiceModal();
    }

    // Animated GPS Canvas
    let animId;
    function initGpsCanvas() {
      const canvas = document.getElementById('gpsCanvas');
      if (!canvas) return;
      const ctx = canvas.getContext('2d');
      canvas.width = canvas.parentElement.clientWidth;
      canvas.height = canvas.parentElement.clientHeight;

      let t = 0;
      function draw() {
        t += 0.015;
        ctx.fillStyle = '#0F172A';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Streets
        ctx.strokeStyle = '#334155';
        ctx.lineWidth = 4;
        ctx.beginPath();
        ctx.moveTo(0, canvas.height * 0.4);
        ctx.lineTo(canvas.width, canvas.height * 0.4);
        ctx.moveTo(0, canvas.height * 0.7);
        ctx.lineTo(canvas.width, canvas.height * 0.7);
        ctx.moveTo(canvas.width * 0.35, 0);
        ctx.lineTo(canvas.width * 0.35, canvas.height);
        ctx.moveTo(canvas.width * 0.7, 0);
        ctx.lineTo(canvas.width * 0.7, canvas.height);
        ctx.stroke();

        // Glowing Route
        ctx.strokeStyle = '#FD651E';
        ctx.lineWidth = 5;
        ctx.beginPath();
        ctx.moveTo(canvas.width * 0.15, canvas.height * 0.85);
        ctx.quadraticCurveTo(canvas.width * 0.45, canvas.height * 0.3, canvas.width * 0.85, canvas.height * 0.25);
        ctx.stroke();

        // Car position along curve
        const carX = canvas.width * 0.55;
        const carY = canvas.height * 0.42;

        // Radar pulse
        const radius = 12 + Math.sin(t * 3) * 6;
        ctx.fillStyle = 'rgba(253, 101, 30, 0.3)';
        ctx.beginPath();
        ctx.arc(carX, carY, radius, 0, Math.PI * 2);
        ctx.fill();

        // Car dot
        ctx.fillStyle = '#FFFFFF';
        ctx.beginPath();
        ctx.arc(carX, carY, 7, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillStyle = '#FD651E';
        ctx.beginPath();
        ctx.arc(carX, carY, 4, 0, Math.PI * 2);
        ctx.fill();

        animId = requestAnimationFrame(draw);
      }
      cancelAnimationFrame(animId);
      draw();
    }
  </script>
</body>
</html>`;
}

server.listen(PORT, HOST, () => {
  console.log(`DRIVEE Server running on http://${HOST}:${PORT}`);
});

# Reflex  

Reflex is an experimental Android app where I’m building the kind of automation features I always wished existed natively.  
The idea is simple: when an event happens on your device (like an SMS arrives, or your device state changes), Reflex can react intelligently — whether that’s replying, sharing device info, or running custom actions.  

The **SMS → Device Info → Auto-Reply** flow is just the first step. Many more features are planned.  

---

## Current Features  
- 📩 **SMS via Notifications**  
  Catch incoming SMS by listening to their notifications (since direct SMS access is restricted on newer Android versions).  

- 👤 **Contact Matching**  
  Extract sender info from the notification and resolve it back to the actual phone number using the contacts provider.  

- 🔄 **Auto-Reply with Device Info**  
  Trigger a background service that gathers battery, network, device, and location details, and sends them as an SMS reply.  

---

## Vision  
Reflex is meant to become a **toolbox for Android automation** — starting with SMS, but expanding to more device-level triggers and intelligent reactions.  

Ideas on the roadmap:  
- Context-aware auto replies (time, location, network conditions)  
- Smart rules for different contacts or keywords  
- Device status reports on demand  
- Extending automation beyond SMS to other events/notifications  

---

## Tech Stack  
- **Kotlin** + Jetpack Compose (UI)  
- **NotificationListenerService** for event capture  
- **Foreground Service** for background actions  
- **Contacts Provider** for name → number resolution  
- **Permissions**:  
  - `SEND_SMS`  
  - `READ_CONTACTS`  
  - `POST_NOTIFICATIONS` (Android 13+)  
  - `FOREGROUND_SERVICE_*` (Android 14+)  

---

## Project Status  
🚧 Reflex is an active work in progress.  
Right now it’s focused on SMS → Device Info → Reply, but the foundation is in place to add many more automation flows.  

---

## Getting Started  
1. Clone the repo  
2. Open in Android Studio (Giraffe or newer recommended)  
3. Run on a device/emulator with notification listener + SMS permissions enabled  
4. Send an SMS to the device and see Reflex respond  

---

## Disclaimer  
This is a **personal learning and experimentation project**.  
Some features require sensitive permissions (like notification access, SMS, and contacts). It’s not designed for production or Play Store release.  

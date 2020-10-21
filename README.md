# Verteilte-Systeme
**Wie werden die Seiten des HTTP Servers aufgerufen?**

Mit http://localhost/server/ werden alle Sensoren angezeigt und ihr aktueller Wert.
Mit http://localhost/server/luftfeuchtigkeit/1 (weil es für diese Art von Sensor zwei Sensoren gibt) oder http://localhost/server/helligkeit kann auf den Messwert von einem
Sensor zugegriffen werden.

Mit http://localhost/server/history/... (z.B. http://localhost/server/history/helligkeit) kann auf die Datenhistorie der einzelnen Sensoren zugegriffen werden.

**Welcher Broker wird für MQTT benutzt?**

Um die MQTT Funktionalität zu gewährleisten wurde der [Mosquitto](https://mosquitto.org) Broker benutzt.

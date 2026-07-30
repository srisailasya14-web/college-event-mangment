document.addEventListener('DOMContentLoaded', () => {
  const events = JSON.parse(localStorage.getItem('events') || '[]');
  const registrations = JSON.parse(localStorage.getItem('registrations') || '[]');
  const form = document.getElementById('adminForm');
  const eventsContainer = document.getElementById('adminEventsContainer');
  const registrationsContainer = document.getElementById('adminRegistrationsContainer');

  function renderAdminContent() {
    if (eventsContainer) {
      eventsContainer.innerHTML = events.length
        ? events.map((event) => `
            <article class="card">
              <h3>${event.name}</h3>
              <p>${event.description}</p>
              <p><strong>Date:</strong> ${event.date}</p>
              <p><strong>Seats:</strong> ${event.seats}</p>
              <button class="btn" onclick="editEvent(${event.id})">Edit</button>
              <button class="btn secondary" onclick="deleteEvent(${event.id})">Delete</button>
            </article>
          `).join('')
        : '<div class="card">No events available.</div>';
    }

    if (registrationsContainer) {
      registrationsContainer.innerHTML = registrations.length
        ? registrations.map((registration) => `
            <article class="card">
              <h3>Registration #${registration.id}</h3>
              <p>Event ID: ${registration.eventId}</p>
            </article>
          `).join('')
        : '<div class="card">No registrations yet.</div>';
    }
  }

  if (form) {
    form.addEventListener('submit', (event) => {
      event.preventDefault();
      const eventId = document.getElementById('eventId').value;
      const payload = {
        id: eventId ? Number(eventId) : Date.now(),
        name: document.getElementById('eventName').value,
        description: document.getElementById('description').value,
        date: document.getElementById('eventDate').value,
        venue: document.getElementById('venue').value,
        seats: Number(document.getElementById('availableSeats').value),
        category: document.getElementById('category').value
      };

      if (eventId) {
        const index = events.findIndex((entry) => entry.id === Number(eventId));
        if (index >= 0) {
          events[index] = payload;
        }
      } else {
        events.push(payload);
      }

      localStorage.setItem('events', JSON.stringify(events));
      form.reset();
      renderAdminContent();
    });
  }

  window.editEvent = function (id) {
    const event = events.find((entry) => entry.id === id);
    if (!event) return;
    document.getElementById('eventId').value = event.id;
    document.getElementById('eventName').value = event.name;
    document.getElementById('description').value = event.description;
    document.getElementById('eventDate').value = event.date;
    document.getElementById('venue').value = event.venue;
    document.getElementById('availableSeats').value = event.seats;
    document.getElementById('category').value = event.category;
  };

  window.deleteEvent = function (id) {
    const updated = events.filter((entry) => entry.id !== id);
    localStorage.setItem('events', JSON.stringify(updated));
    window.location.reload();
  };

  renderAdminContent();
});

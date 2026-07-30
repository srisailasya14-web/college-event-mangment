document.addEventListener('DOMContentLoaded', () => {
  const student = JSON.parse(localStorage.getItem('loggedInStudent') || 'null');
  const studentName = document.getElementById('studentName');
  if (studentName && student) {
    studentName.textContent = student.name;
  }

  const totalEvents = document.getElementById('totalEvents');
  const registeredEvents = document.getElementById('registeredEvents');
  const upcomingEvents = document.getElementById('upcomingEvents');
  const logoutBtn = document.getElementById('logoutBtn');

  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      localStorage.removeItem('loggedInStudent');
    });
  }

  const events = JSON.parse(localStorage.getItem('events') || '[]');
  const registrations = JSON.parse(localStorage.getItem('registrations') || '[]');

  if (totalEvents) totalEvents.textContent = events.length;
  if (registeredEvents) registeredEvents.textContent = registrations.length;
  if (upcomingEvents) upcomingEvents.textContent = events.filter((event) => new Date(event.date) > new Date()).length;

  const registrationsContainer = document.getElementById('registrationsContainer');
  if (registrationsContainer) {
    renderRegistrations(registrations, events);
  }
});

function renderRegistrations(registrations, events) {
  const container = document.getElementById('registrationsContainer');
  if (!container) return;

  if (!registrations.length) {
    container.innerHTML = '<div class="card">No registrations yet.</div>';
    return;
  }

  container.innerHTML = registrations.map((registration) => {
    const event = events.find((entry) => entry.id === registration.eventId);
    return `
      <div class="card">
        <h3>${event?.name || 'Event deleted'}</h3>
        <p>${event?.description || 'No description available'}</p>
        <p><strong>Date:</strong> ${event?.date || 'N/A'}</p>
        <button class="btn secondary" data-id="${registration.id}">Cancel</button>
      </div>
    `;
  }).join('');

  container.querySelectorAll('button').forEach((button) => {
    button.addEventListener('click', () => {
      const id = Number(button.getAttribute('data-id'));
      const updated = registrations.filter((entry) => entry.id !== id);
      localStorage.setItem('registrations', JSON.stringify(updated));
      window.location.reload();
    });
  });
}

document.addEventListener('DOMContentLoaded', () => {
  const events = [
    {
      id: 1,
      name: 'Tech Fest 2026',
      description: 'Hackathons, coding challenges, and innovation showcases.',
      date: '2026-09-15',
      venue: 'Main Auditorium',
      seats: 120,
      category: 'Technical'
    },
    {
      id: 2,
      name: 'Cultural Night',
      description: 'Music, dance, drama, and vibrant performances.',
      date: '2026-10-02',
      venue: 'Open Air Theatre',
      seats: 80,
      category: 'Cultural'
    },
    {
      id: 3,
      name: 'Sports Meet',
      description: 'Inter-department sports and team competitions.',
      date: '2026-10-20',
      venue: 'Sports Complex',
      seats: 150,
      category: 'Sports'
    }
  ];

  localStorage.setItem('events', JSON.stringify(events));

  const container = document.getElementById('eventsContainer');
  const searchInput = document.getElementById('searchInput');
  const filterSelect = document.getElementById('filterSelect');
  const sortSelect = document.getElementById('sortSelect');

  function renderEvents() {
    const search = searchInput.value.toLowerCase();
    const filter = filterSelect.value;
    const sortBy = sortSelect.value;

    let filtered = events.filter((event) => {
      const matchesSearch = event.name.toLowerCase().includes(search);
      const matchesFilter = filter === 'all' || event.category === filter;
      return matchesSearch && matchesFilter;
    });

    filtered = filtered.sort((a, b) => {
      if (sortBy === 'seats') return a.seats - b.seats;
      if (sortBy === 'name') return a.name.localeCompare(b.name);
      return new Date(a.date) - new Date(b.date);
    });

    if (!container) return;
    container.innerHTML = filtered.map((event) => `
      <article class="card">
        <h3>${event.name}</h3>
        <p>${event.description}</p>
        <p><strong>Date:</strong> ${event.date}</p>
        <p><strong>Venue:</strong> ${event.venue}</p>
        <p><strong>Available Seats:</strong> ${event.seats}</p>
        <button class="btn register-btn" data-id="${event.id}">Register</button>
      </article>
    `).join('');

    container.querySelectorAll('.register-btn').forEach((button) => {
      button.addEventListener('click', () => {
        const id = Number(button.getAttribute('data-id'));
        const registrations = JSON.parse(localStorage.getItem('registrations') || '[]');
        const alreadyRegistered = registrations.some((entry) => entry.eventId === id);
        if (alreadyRegistered) {
          alert('You have already registered for this event.');
          return;
        }
        const updated = events.find((entry) => entry.id === id);
        if (updated && updated.seats > 0) {
          updated.seats -= 1;
          registrations.push({ id: Date.now(), eventId: id });
          localStorage.setItem('registrations', JSON.stringify(registrations));
          localStorage.setItem('events', JSON.stringify(events));
          renderEvents();
        } else {
          alert('No seats available.');
        }
      });
    });
  }

  [searchInput, filterSelect, sortSelect].forEach((control) => {
    control.addEventListener('input', renderEvents);
    control.addEventListener('change', renderEvents);
  });

  renderEvents();
});

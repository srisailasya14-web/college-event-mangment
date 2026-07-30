document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('contactForm');
  if (!form) return;

  form.addEventListener('submit', (event) => {
    event.preventDefault();
    clearErrors(form);

    const data = {
      name: document.getElementById('contactName').value,
      email: document.getElementById('contactEmail').value,
      subject: document.getElementById('subject').value,
      message: document.getElementById('message').value
    };

    let valid = true;

    if (!validateRequired(data.name)) {
      showError('contactName', 'Name is required.');
      valid = false;
    }
    if (!validateEmail(data.email)) {
      showError('contactEmail', 'Please enter a valid email.');
      valid = false;
    }
    if (!validateRequired(data.subject)) {
      showError('subject', 'Subject is required.');
      valid = false;
    }
    if (!validateRequired(data.message)) {
      showError('message', 'Message is required.');
      valid = false;
    }

    if (!valid) return;

    const messages = JSON.parse(localStorage.getItem('contactMessages') || '[]');
    messages.push({ id: Date.now(), ...data });
    localStorage.setItem('contactMessages', JSON.stringify(messages));
    alert('Message sent successfully.');
    form.reset();
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    clearErrors(form);

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if (!validateEmail(email)) {
      showError('email', 'Please enter a valid email address.');
      return;
    }

    if (!validateRequired(password)) {
      showError('password', 'Password is required.');
      return;
    }

    if (email === 'admin@campussphere.edu' && password === 'admin123') {
      localStorage.setItem('loggedInAdmin', 'true');
      window.location.href = 'admin.html';
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams({ email, password })
      });

      const result = await response.json();
      if (result.success) {
        localStorage.setItem('loggedInStudent', JSON.stringify({ email }));
        window.location.href = 'dashboard.html';
      } else {
        showError('password', result.message || 'Invalid email or password.');
      }
    } catch (error) {
      showError('password', 'Unable to reach the authentication server.');
    }
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registerForm');
  if (!form) return;

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    clearErrors(form);

    const data = {
      name: document.getElementById('name').value.trim(),
      rollNumber: document.getElementById('rollNumber').value.trim(),
      department: document.getElementById('department').value.trim(),
      year: document.getElementById('year').value,
      email: document.getElementById('email').value.trim(),
      phone: document.getElementById('phone').value.trim(),
      password: document.getElementById('password').value
    };

    let valid = true;

    if (!validateRequired(data.name)) {
      showError('name', 'Full name is required.');
      valid = false;
    }
    if (!validateRequired(data.rollNumber)) {
      showError('rollNumber', 'Roll number is required.');
      valid = false;
    }
    if (!validateRequired(data.department)) {
      showError('department', 'Department is required.');
      valid = false;
    }
    if (!validateRequired(data.year)) {
      showError('year', 'Year is required.');
      valid = false;
    }
    if (!validateEmail(data.email)) {
      showError('email', 'Please enter a valid email address.');
      valid = false;
    }
    if (!validatePhone(data.phone)) {
      showError('phone', 'Phone number must be 10 digits.');
      valid = false;
    }
    if (!validatePassword(data.password)) {
      showError('password', 'Password must be at least 8 characters.');
      valid = false;
    }

    if (!valid) {
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams(data)
      });

      const result = await response.json();
      if (result.success) {
        localStorage.setItem('loggedInStudent', JSON.stringify({ email: data.email, name: data.name }));
        window.location.href = 'dashboard.html';
        return;
      }

      showError('email', result.message || 'Registration failed.');
    } catch (error) {
      const fallbackKey = `student:${data.email}`;
      localStorage.setItem(fallbackKey, JSON.stringify(data));
      localStorage.setItem('loggedInStudent', JSON.stringify({ email: data.email, name: data.name }));
      window.location.href = 'dashboard.html';
    }
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', (event) => {
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

    const students = JSON.parse(localStorage.getItem('students') || '[]');
    const student = students.find((entry) => entry.email === email && entry.password === password);

    if (student) {
      localStorage.setItem('loggedInStudent', JSON.stringify({ email: student.email, name: student.name }));
      window.location.href = 'dashboard.html';
    } else {
      showError('password', 'Invalid email or password.');
    }
  });
});

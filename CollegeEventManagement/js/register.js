document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registerForm');
  if (!form) return;

  form.addEventListener('submit', (event) => {
    event.preventDefault();
    clearErrors(form);

    const data = {
      name: document.getElementById('name').value,
      rollNumber: document.getElementById('rollNumber').value,
      department: document.getElementById('department').value,
      year: document.getElementById('year').value,
      email: document.getElementById('email').value,
      phone: document.getElementById('phone').value,
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

    const students = JSON.parse(localStorage.getItem('students') || '[]');
    const existing = students.some((student) => student.email === data.email);
    if (existing) {
      showError('email', 'This email is already registered.');
      return;
    }

    students.push({ ...data, id: Date.now() });
    localStorage.setItem('students', JSON.stringify(students));
    localStorage.setItem('loggedInStudent', JSON.stringify({ email: data.email, name: data.name }));
    window.location.href = 'dashboard.html';
  });
});

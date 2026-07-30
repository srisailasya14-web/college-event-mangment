function showError(fieldId, message) {
  const field = document.getElementById(fieldId);
  if (!field) return;
  const existing = field.parentElement.querySelector('.error-message');
  if (existing) existing.remove();
  const error = document.createElement('div');
  error.className = 'error-message';
  error.textContent = message;
  field.parentElement.appendChild(error);
}

function clearErrors(form) {
  form.querySelectorAll('.error-message').forEach((el) => el.remove());
}

function validateEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}

function validatePhone(phone) {
  return /^\d{10}$/.test(phone);
}

function validatePassword(password) {
  return password.length >= 8;
}

function validateRequired(value) {
  return value.trim() !== '';
}

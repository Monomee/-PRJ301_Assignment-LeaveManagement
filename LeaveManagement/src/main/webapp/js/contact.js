document.addEventListener('DOMContentLoaded', function() {
  const rows = document.querySelectorAll('.contact-table tbody tr');
  rows.forEach(row => {
    row.addEventListener('click', function() {
      rows.forEach(r => r.classList.remove('row-active'));
      row.classList.add('row-active');
    });
  });
}); 
document.addEventListener('DOMContentLoaded', function() {
  const navbar = document.querySelector('.navbar');
  let lastScroll = 0;
  window.addEventListener('scroll', function() {
    const currentScroll = window.pageYOffset;
    if (currentScroll > 40) {
      navbar.style.boxShadow = '0 4px 16px rgba(0,0,0,0.12)';
      navbar.style.transform = 'scaleY(0.97)';
      navbar.style.background = 'linear-gradient(90deg, #4e54c8 0%, #8f94fb 100%)';
    } else {
      navbar.style.boxShadow = '0 2px 8px rgba(0,0,0,0.07)';
      navbar.style.transform = 'scaleY(1)';
      navbar.style.background = 'linear-gradient(90deg, #4e54c8 0%, #8f94fb 100%)';
    }
    lastScroll = currentScroll;
  });

  // Highlight active navbar link
  const links = document.querySelectorAll('.navbar-link');
  const path = window.location.pathname;
  links.forEach(link => {
    // Xử lý các đường dẫn đặc biệt
    if (
      (path === '/home' && link.getAttribute('href') === '/home') ||
      (path === '/news' && link.getAttribute('href') === '/news') ||
      (path === '/contact' && link.getAttribute('href') === '/contact') ||
      (path === '/tools/leave' && link.getAttribute('href') === '/tools/leave') ||
      (path.startsWith('/tools') && link.getAttribute('href').startsWith('/tools')) ||
      (path.startsWith('/leave') && link.getAttribute('href').startsWith('/leave'))
    ) {
      link.classList.add('active');
    } else {
      link.classList.remove('active');
    }
  });
}); 
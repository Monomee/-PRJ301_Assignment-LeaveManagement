/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Feature.js - hiệu ứng cho các trang leave, đồng bộ với navbar

document.addEventListener('DOMContentLoaded', function() {
  // Fade-in effect for card/container
  const cards = document.querySelectorAll('.card, .leave-container, .leave-table-container');
  cards.forEach(card => {
    card.style.opacity = 0;
    card.style.transform = 'translateY(24px)';
    setTimeout(() => {
      card.style.transition = 'opacity 0.7s cubic-bezier(.4,2,.3,1), transform 0.7s cubic-bezier(.4,2,.3,1)';
      card.style.opacity = 1;
      card.style.transform = 'translateY(0)';
    }, 100);
  });

  // Button ripple effect
  document.querySelectorAll('button, input[type="submit"]')
    .forEach(btn => {
      btn.addEventListener('click', function(e) {
        const circle = document.createElement('span');
        circle.className = 'ripple';
        this.appendChild(circle);
        const d = Math.max(this.clientWidth, this.clientHeight);
        circle.style.width = circle.style.height = d + 'px';
        circle.style.left = (e.offsetX - d/2) + 'px';
        circle.style.top = (e.offsetY - d/2) + 'px';
        setTimeout(() => circle.remove(), 600);
      });
    });

  // Highlight table row on hover (for leave/contact tables)
  document.querySelectorAll('.leave-table tr, .contact-table tr').forEach(row => {
    row.addEventListener('mouseenter', function() {
      this.style.boxShadow = '0 2px 12px rgba(78,84,200,0.10)';
      this.style.zIndex = 2;
      this.style.position = 'relative';
    });
    row.addEventListener('mouseleave', function() {
      this.style.boxShadow = '';
      this.style.zIndex = '';
      this.style.position = '';
    });
  });
});

// CSS for ripple effect (inject if not present)
(function() {
  if (!document.getElementById('feature-ripple-style')) {
    const style = document.createElement('style');
    style.id = 'feature-ripple-style';
    style.innerHTML = `
      .ripple {
        position: absolute;
        border-radius: 50%;
        background: rgba(143,148,251,0.25);
        transform: scale(0);
        animation: ripple-anim 0.6s linear;
        pointer-events: none;
        z-index: 10;
      }
      @keyframes ripple-anim {
        to {
          transform: scale(2.5);
          opacity: 0;
        }
      }
      button, input[type="submit"] { position: relative; overflow: hidden; }
    `;
    document.head.appendChild(style);
  }
})();



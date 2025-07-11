// Leave Application Pages Interactivity - đồng bộ, thân thiện

document.addEventListener('DOMContentLoaded', function() {
    // Fade-in effect for card
    document.querySelectorAll('.leave-card').forEach((card, idx) => {
        card.style.opacity = 0;
        card.style.transform = 'translateY(30px)';
        setTimeout(() => {
            card.style.transition = 'opacity 0.6s cubic-bezier(.4,2,.6,1), transform 0.6s cubic-bezier(.4,2,.6,1)';
            card.style.opacity = 1;
            card.style.transform = 'translateY(0)';
        }, 120 * idx + 100);
    });

    // Highlight table row on hover (nếu chưa có feature.js)
    document.querySelectorAll('.leave-table tr').forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.background = '#e6e9f7';
        });
        row.addEventListener('mouseleave', function() {
            this.style.background = '';
        });
    });

    // Button ripple effect (nếu chưa có feature.js)
    document.querySelectorAll('.leave-btn, button, input[type="submit"]').forEach(btn => {
        btn.addEventListener('click', function(e) {
            let ripple = document.createElement('span');
            ripple.className = 'ripple';
            ripple.style.left = (e.offsetX || 0) + 'px';
            ripple.style.top = (e.offsetY || 0) + 'px';
            this.appendChild(ripple);
            setTimeout(() => ripple.remove(), 600);
        });
    });

    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', function(e) {
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                e.preventDefault();
                target.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });
});

// Ripple effect CSS (inject nếu chưa có)
(function() {
    if (!document.getElementById('ripple-style')) {
        const style = document.createElement('style');
        style.id = 'ripple-style';
        style.textContent = `.ripple { position: absolute; border-radius: 50%; background: rgba(143,148,251,0.18); transform: scale(0); animation: ripple 0.6s linear; pointer-events: none; width: 100px; height: 100px; left: 50%; top: 50%; margin-left: -50px; margin-top: -50px; z-index: 2; } @keyframes ripple { to { transform: scale(2.5); opacity: 0; } } button, input[type='submit'] { position: relative; overflow: hidden; }`;
        document.head.appendChild(style);
    }
})(); 
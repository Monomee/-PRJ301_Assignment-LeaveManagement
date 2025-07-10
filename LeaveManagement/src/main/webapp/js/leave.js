// Leave Application Pages Interactivity

document.addEventListener('DOMContentLoaded', function() {
    // Animate form input focus
    document.querySelectorAll('.form-group input, .form-group textarea, .form-group select').forEach(el => {
        el.addEventListener('focus', function() {
            this.style.background = '#eaf6fb';
        });
        el.addEventListener('blur', function() {
            this.style.background = '';
        });
    });

    // Button ripple effect
    document.querySelectorAll('button, input[type="submit"]').forEach(btn => {
        btn.addEventListener('click', function(e) {
            let ripple = document.createElement('span');
            ripple.className = 'ripple';
            ripple.style.left = (e.offsetX || 0) + 'px';
            ripple.style.top = (e.offsetY || 0) + 'px';
            this.appendChild(ripple);
            setTimeout(() => ripple.remove(), 600);
        });
    });

    // Table row highlight on hover
    document.querySelectorAll('.leave-table tr').forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.background = '#eaf6fb';
        });
        row.addEventListener('mouseleave', function() {
            this.style.background = '';
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

    // Helper: Show/hide messages
    window.showMessage = function(msg, type = 'message') {
        let el = document.createElement('div');
        el.className = type;
        el.textContent = msg;
        document.body.prepend(el);
        setTimeout(() => el.remove(), 3500);
    };
});

// Ripple effect CSS (inject if not present)
(function() {
    if (!document.getElementById('ripple-style')) {
        const style = document.createElement('style');
        style.id = 'ripple-style';
        style.textContent = `.ripple { position: absolute; border-radius: 50%; background: rgba(41,128,185,0.3); transform: scale(0); animation: ripple 0.6s linear; pointer-events: none; width: 100px; height: 100px; left: 50%; top: 50%; margin-left: -50px; margin-top: -50px; z-index: 2; } @keyframes ripple { to { transform: scale(2.5); opacity: 0; } } button, input[type='submit'] { position: relative; overflow: hidden; }`;
        document.head.appendChild(style);
    }
})(); 
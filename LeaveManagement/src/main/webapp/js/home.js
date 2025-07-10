// Home Page Interactivity

document.addEventListener('DOMContentLoaded', function() {
    // Greeting animation
    const welcome = document.querySelector('.welcome');
    if (welcome) {
        welcome.style.opacity = 0;
        setTimeout(() => {
            welcome.style.transition = 'opacity 1s';
            welcome.style.opacity = 1;
        }, 300);
    }

    // Card hover effect (for touch of interactivity)
    const cards = document.querySelectorAll('.card');
    cards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.boxShadow = '0 8px 32px rgba(44,62,80,0.18)';
            card.style.transform = 'translateY(-4px) scale(1.02)';
            card.style.transition = 'box-shadow 0.3s, transform 0.3s';
        });
        card.addEventListener('mouseleave', () => {
            card.style.boxShadow = '';
            card.style.transform = '';
        });
    });

    // Smooth scroll to features (if a button is added in the future)
    const toFeaturesBtn = document.getElementById('to-features-btn');
    if (toFeaturesBtn) {
        toFeaturesBtn.addEventListener('click', function(e) {
            e.preventDefault();
            const featuresSection = document.querySelector('.features-card');
            if (featuresSection) {
                featuresSection.scrollIntoView({ behavior: 'smooth' });
            }
        });
    }
}); 
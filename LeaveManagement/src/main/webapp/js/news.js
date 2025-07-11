document.addEventListener('DOMContentLoaded', function() {
  const cards = document.querySelectorAll('.news-card');
  cards.forEach((card, idx) => {
    card.style.opacity = 0;
    card.style.transform = 'translateY(30px)';
    setTimeout(() => {
      card.style.transition = 'opacity 0.6s cubic-bezier(.4,2,.6,1), transform 0.6s cubic-bezier(.4,2,.6,1)';
      card.style.opacity = 1;
      card.style.transform = 'translateY(0)';
    }, 120 * idx + 100);
  });
}); 
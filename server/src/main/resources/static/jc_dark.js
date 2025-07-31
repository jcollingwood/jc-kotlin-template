// Mobile Navigation Toggle
function toggleMobileNav() {
    const mobileNav = document.getElementById('mobileNav');
    mobileNav.classList.toggle('active');
    document.body.style.overflow = mobileNav.classList.contains('active') ? 'hidden' : '';
}

// Smooth Scrolling for Navigation Links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            const headerHeight = document.querySelector('header').offsetHeight;
            const targetPosition = target.getBoundingClientRect().top + window.pageYOffset - headerHeight;

            window.scrollTo({
                top: targetPosition,
                behavior: 'smooth'
            });
        }
    });
});

// Form Submission Handler
document.querySelector('.contact-form').addEventListener('submit', function(e) {
    e.preventDefault();

    // Create and show a simple toast notification
    const toast = document.createElement('div');
    toast.style.cssText = `
        position: fixed;
        top: 2rem;
        right: 2rem;
        background: rgba(20, 20, 20, 0.95);
        backdrop-filter: blur(20px);
        padding: 1rem 2rem;
        color: #a8e6cf;
        font-weight: 300;
        z-index: 1001;
        transform: translateX(100%);
        transition: transform 0.3s ease;
        border-top: 1px solid rgba(168, 230, 207, 0.3);
    `;
    toast.textContent = 'Message sent successfully!';
    document.body.appendChild(toast);

    // Animate in
    setTimeout(() => {
        toast.style.transform = 'translateX(0)';
    }, 100);

    // Remove after 3 seconds
    setTimeout(() => {
        toast.style.transform = 'translateX(100%)';
        setTimeout(() => {
            document.body.removeChild(toast);
        }, 300);
    }, 3000);

    // Reset form
    this.reset();
});

// Scroll-based animations
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('fade-in');
        }
    });
}, observerOptions);

// Observe elements for animation
document.querySelectorAll('.service-card, .portfolio-item, .stat-item, .contact-item').forEach(el => {
    observer.observe(el);
});

// Header background on scroll
window.addEventListener('scroll', () => {
    const header = document.querySelector('header');
    if (window.scrollY > 100) {
        header.style.background = 'rgba(10, 10, 10, 0.95)';
    } else {
        header.style.background = 'rgba(10, 10, 10, 0.9)';
    }
});

// Close mobile nav when clicking outside
document.addEventListener('click', (e) => {
    const mobileNav = document.getElementById('mobileNav');
    const menuToggle = document.querySelector('.menu-toggle');

    if (mobileNav.classList.contains('active') &&
        !mobileNav.contains(e.target) &&
        !menuToggle.contains(e.target)) {
        toggleMobileNav();
    }
});

// Portfolio item hover effects
document.querySelectorAll('.portfolio-item').forEach(item => {
    item.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-8px)';
    });

    item.addEventListener('mouseleave', function() {
        this.style.transform = 'translateY(-4px)';
    });
});

// Add click handlers for portfolio items (could open modals in real implementation)
document.querySelectorAll('.portfolio-item').forEach(item => {
    item.addEventListener('click', function() {
        const title = this.querySelector('.portfolio-title').textContent;

        // Simple alert for demo - in real implementation, this would open a modal
        const notification = document.createElement('div');
        notification.style.cssText = `
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: rgba(20, 20, 20, 0.95);
            backdrop-filter: blur(20px);
            padding: 2rem;
            color: #fff;
            font-weight: 300;
            z-index: 1001;
            text-align: center;
            border-top: 1px solid rgba(168, 230, 207, 0.3);
            min-width: 300px;
        `;
        notification.innerHTML = `
            <h3 style="margin-bottom: 1rem; color: #a8e6cf;">${title}</h3>
            <p style="color: #bbb; margin-bottom: 2rem;">Portfolio detail view would open here</p>
            <button onclick="this.parentElement.remove()" style="background: rgba(168, 230, 207, 0.1); border: none; color: #a8e6cf; padding: 0.5rem 1rem; cursor: pointer;">Close</button>
        `;
        document.body.appendChild(notification);

        // Auto remove after 3 seconds
        setTimeout(() => {
            if (document.body.contains(notification)) {
                notification.remove();
            }
        }, 3000);
    });
});

// Parallax effect for hero background lines
window.addEventListener('scroll', () => {
    const scrolled = window.pageYOffset;
    const parallax = scrolled * 0.5;

    document.querySelectorAll('.hero-line').forEach((line, index) => {
        line.style.transform = `translateX(${parallax + index * 20}px)`;
    });
});

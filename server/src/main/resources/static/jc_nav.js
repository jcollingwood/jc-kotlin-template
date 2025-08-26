// Mobile Navigation Toggle
function toggleMobileNav() {
    console.log('toggling mobile nav')
    const mobileNav = document.getElementById('mobileNav');
    mobileNav.classList.toggle('active');
    document.body.style.overflow = mobileNav.classList.contains('active') ? 'hidden' : '';
}
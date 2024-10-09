document.addEventListener('DOMContentLoaded', function () {
    const hamburger = document.querySelector('.hamburger-menu');
    const dropdown = document.querySelector('.dropdown');
    const crossSign = document.querySelector('.cross-sign');
    const navLinks = document.querySelectorAll('.dropdown .nav-links li.section');
    const scrollButton = document.querySelector('.bi-chevron-up');
    const rsvpForm = document.querySelector('form');
    const customSelectContainer = document.querySelector('.custom-select-container');
    const selectBox = customSelectContainer.querySelector('.select-box');
    const selectOptions = customSelectContainer.querySelectorAll('.select-options input');
    const plFlags = document.querySelectorAll('.pl-flag');
    const enFlags = document.querySelectorAll('.en-flag');
    const langElements = document.querySelectorAll('.lang');
    let submitButton = document.getElementById('submit-button');

    selectBox.addEventListener('click', function () {
        customSelectContainer.classList.toggle('active');
    });

    document.addEventListener('click', function (e) {
        if (!customSelectContainer.contains(e.target)) {
            customSelectContainer.classList.remove('active');
        }
    });

    rsvpForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(event.target);
        let alcoholPreferences = [];

        // Add custom select options to formData
        selectOptions.forEach(option => {
            if (option.checked) {
                alcoholPreferences.push(option.value);
            }
        });

        const value = Object.fromEntries(formData.entries());
        value.alcoholPreferences = alcoholPreferences;
        const json = JSON.stringify(value);
        fetch("/api/v1/attendee", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                const currentLanguage = localStorage.getItem('language') || 'pl';
                let successMessage = '';

                if (currentLanguage === 'pl') {
                    successMessage = 'Dziękujemy, do zobaczenia na weselu!';
                } else if (currentLanguage === 'en') {
                    successMessage = 'Thank you, see you at the wedding!';
                }

                submitButton.disabled = true;
                submitButton.value = successMessage;
                submitButton.style.backgroundColor = '#4CAF50'; // Change to a darker green color
                submitButton.style.color = 'white'; // Change text color to white for better contrast
                submitButton.style.cursor = 'not-allowed'; // Change cursor to indicate disabled state
            } else {
                alert('Failed to submit RSVP. Please try again.');
            }
        }).catch(error => {
            alert('An error occurred: ' + error.message);
        });

        rsvpForm.reset();
    });

    hamburger.addEventListener('click', () => {
        dropdown.classList.toggle('open');
    });

    crossSign.addEventListener('click', () => {
        dropdown.classList.remove('open');
    });

    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            setTimeout(() => {
                dropdown.classList.remove('open');
            }, 500);
        });
    });

    scrollButton.addEventListener('click', function () {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    rsvpForm.reset();

    window.onscroll = function() {
        const scrollButton = document.querySelector(".bi-chevron-up");
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            scrollButton.style.display = "block";
        } else {
            scrollButton.style.display = "none";
        }
    };

    function changeLanguage(lang) {
        if (lang === 'pl') {
            langElements.forEach(function (element) {
                element.textContent = element.getAttribute('data-lang-pl');
            });
        } else if (lang === 'en') {
            langElements.forEach(function (element) {
                element.textContent = element.getAttribute('data-lang-en');
            });
        }
        submitButton.value = submitButton.getAttribute('data-lang-' + lang);
        localStorage.setItem('language', lang);
        enableSubmitFormButton(lang);
    }

    function enableSubmitFormButton(lang) {
        submitButton.disabled = false;
        submitButton.value = submitButton.getAttribute('data-lang-' + lang);
        submitButton.style.backgroundColor = '#b3b3b3'; // Change to a darker green color
        submitButton.style.color = 'white'; // Change text color to white for better contrast
        submitButton.style.cursor = 'pointer'; // Change cursor to indicate disabled state
    }

    plFlags.forEach(function (flag) {
        flag.addEventListener('click', function () {
            changeLanguage('pl');
        });
    });

    enFlags.forEach(function (flag) {
        flag.addEventListener('click', function () {
            changeLanguage('en');
        });
    });
});
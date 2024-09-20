document.addEventListener('DOMContentLoaded', function () {
    const hamburger = document.querySelector('.hamburger-menu');
    const dropdown = document.querySelector('.dropdown');
    const crossSign = document.querySelector('.cross-sign');
    const navLinks = document.querySelectorAll('.dropdown .nav-links li');
    const scrollButton = document.querySelector('.bi-chevron-up');
    const rsvpForm = document.querySelector('form');
    const customSelectContainer = document.querySelector('.custom-select-container');
    const selectBox = customSelectContainer.querySelector('.select-box');
    const selectOptions = customSelectContainer.querySelectorAll('.select-options input');

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
                const submitButton = document.getElementById('submit-button');
                submitButton.disabled = true;
                submitButton.value = 'Dziękujemy!';
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
});
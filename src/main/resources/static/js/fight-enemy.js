import {performPostRequest} from '/js/utility/requests.js';

(function fightEnemy() {
    const heroHealthBarFillElement = document.getElementById('hero-health-bar-fill');

    window.heroHealthBar = heroHealthBarFillElement;

    document.getElementById('attack-btn')
        .addEventListener('click', async () => {
            const data = await performPostRequest('http://localhost:8080/enemies/attack');

            console.log(data);
        })
})()


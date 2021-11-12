(function selectHero() {
    const heroImageElement = document.getElementById('hero-image');
    const heroInformationElement = document.getElementById('hero-information');
    const heroDeleteFormElement = document.getElementById('hero-delete-form');
    const heroDeleteElement = document.getElementById('hero-to-delete');

    const WARRIOR_IMAGE = 'https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/warrior-f.png';
    const HUNTER_IMAGE = 'https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/hunter-f.png';
    const MAGE_IMAGE = 'https://res.cloudinary.com/felin/image/upload/v1636280530/DestinyOfTheChosen/heroes/mage-f.png';

    document
        .getElementById('hero-selection')
        .addEventListener('click', (event) => {
            event.target.classList.contains("WARRIOR") ? changeHeroData(WARRIOR_IMAGE, event.target.value) : '';
            event.target.classList.contains("HUNTER") ? changeHeroData(HUNTER_IMAGE, event.target.value) : '';
            event.target.classList.contains("MAGE") ? changeHeroData(MAGE_IMAGE, event.target.value) : '';
        });

    document
        .getElementById('delete-hero-btn')
        .addEventListener('click', (event) => {
           event.preventDefault();

            if (heroDeleteElement.value) heroDeleteFormElement.submit();
        });

    function changeHeroData(heroImage, heroId) {
        let textContent = document.getElementsByClassName(heroId)[0].textContent;
        let newText = textContent.replaceAll(' br ', '<br/>')
        heroImageElement.setAttribute('src', heroImage)
        heroInformationElement.innerHTML = newText;
        heroDeleteElement.value = heroId;
    }
})();


(function selectHero() {
    const heroImageElement = document.getElementById('hero-creation-image');
    const heroDescriptionElement = document.getElementById('hero-description');
    const warriorSelectElement = document.getElementById('WARRIOR');
    const hunterSelectElement = document.getElementById('HUNTER');
    const mageSelectElement = document.getElementById('MAGE');

    document
        .getElementById('hero-select')
        .addEventListener('click', (event) => {

            switch (event.target.id) {
                case ('warrior-select'): {
                    changeHeroData('warrior', warriorDescription);
                    warriorSelectElement.selected = true;
                    break;
                }
                case ('hunter-select'): {
                    changeHeroData('hunter', hunterDescription);
                    hunterSelectElement.selected = true;
                    break;
                }
                case ('mage-select'): {
                    changeHeroData('mage', mageDescription);
                    mageSelectElement.selected = true;
                    break;
                }
            }
        });

    function changeHeroData(heroClass, heroDescription) {
        heroImageElement.setAttribute('src', '/images/' + heroClass + '.png')
        heroDescriptionElement.textContent = heroDescription;
        // heroSelectElement.setAttribute('value', heroClass.toUpperCase());
        // heroSelectElement.setAttribute('text', heroClass.toUpperCase());
        // console.log(heroSelectElement);
    }

    const warriorDescription = 'Warriors are tough, they have a lot of health and armor, ' +
            'but lower attack damage. Most of their skills are made for ' +
            'survival and soaking up damage. Their main stat is Strength which ' +
            'gives them two points in attack damage for each Strength stat point.';

    const hunterDescription = 'Hunters have high attack damage, their normal attack is very ' +
            'powerful as well as their skills. Most of their skills are for damage, ' +
            'but they also have crowd control abilities like traps. Their main stat ' +
            'is Dexterity which gives them two points in attack damage for each ' +
            'Dexterity stat point.';

    const mageDescription = 'Mages deal a lot of damage, but they are have low health and armor, ' +
            'also they need mana in order to cast spells, otherwise their normal ' +
            'attack is very weak. Their main stat is Energy which gives them two ' +
            'points in magic damage for each Energy stat point.';
})();


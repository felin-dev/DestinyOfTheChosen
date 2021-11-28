import {performGetRequest, performPostRequest} from '/js/utility/requests.js';

(function fightEnemy() {
    const heroHealthBarFillElement = document.getElementById('hero-health-bar-fill');
    const heroHealthBarValueElement = document.getElementById('hero-health-bar-value');
    const heroManaBarFillElement = document.getElementById('hero-mana-bar-fill');
    const heroManaBarValueElement = document.getElementById('hero-mana-bar-value');
    const enemyHealthBarFillElement = document.getElementById('enemy-health-bar-fill');
    const enemyHealthBarValueElement = document.getElementById('enemy-health-bar-value');
    const attackBtn = document.getElementById('attack-btn');

    document.getElementById('skills')
        .addEventListener('click', async (event) => {
            if (event.target.tagName === 'IMG' && attackBtn.textContent !== 'Attack New Enemy' &&  attackBtn.textContent !== 'Try Again') {
                const castedSkill = event.target.parentNode.parentNode.querySelector('.casted-skill').textContent;
                const token = await performGetRequest('http://localhost:8080/csrf');
                const combatStatus = await performPostRequest('http://localhost:8080/enemies/skill/attack', {
                    [token.headerName]: [token.csrf]
                }, { skillName: castedSkill });

                updateCombatStatus(combatStatus);
            }
        })

    attackBtn
        .addEventListener('click', async () => {
            if (attackBtn.textContent === 'Attack New Enemy' || attackBtn.textContent === 'Try Again') {
                attackBtn.textContent = 'Attack';
                const newCombatStatus = await performGetRequest('http://localhost:8080/enemies/attack/new-enemy');
                updateCombatStatus(newCombatStatus);
                return;
            }

            const token = await performGetRequest('http://localhost:8080/csrf');
            const combatStatus = await performPostRequest('http://localhost:8080/enemies/attack', {
                [token.headerName]: [token.csrf]
            });

            updateCombatStatus(combatStatus);
        })

    function updateCombatStatus(combatStatus) {
        updateEnemyStatus(combatStatus);
        setTimeout(function () {
            updateHeroStatus(combatStatus)
        }, 700);
    }

    function updateHeroStatus(combatStatus) {
        const hero = combatStatus.hero;
        const heroHpPercentage = Math.round(hero.currentHealth / hero.baseHealth * 100);
        const heroMpPercentage = Math.round(hero.currentMana / hero.baseMana * 100);

        if (heroHpPercentage <= 20) {
            heroHealthBarFillElement.style.backgroundColor = 'red';
        } else if (heroHpPercentage <= 50) {
            heroHealthBarFillElement.style.backgroundColor = 'yellow';
        } else if (heroHpPercentage > 50) {
            heroHealthBarFillElement.style.backgroundColor = '#baf508';
        }

        heroHealthBarFillElement.style.width = heroHpPercentage + '%';
        heroManaBarFillElement.style.width = heroMpPercentage + '%';

        heroHealthBarValueElement.textContent = hero.currentHealth + '/' + hero.baseHealth;
        heroManaBarValueElement.textContent = hero.currentMana + '/' + hero.baseMana;

        if (heroHpPercentage === 0) {
            attackBtn.textContent = 'Try Again'
        }
    }

    function updateEnemyStatus(combatStatus) {
        const enemy = combatStatus.enemy;
        const enemyHpPercentage = Math.round(enemy.currentHealth / enemy.health * 100);

        if (enemyHpPercentage <= 20) {
            enemyHealthBarFillElement.style.backgroundColor = 'red';
        } else if (enemyHpPercentage <= 50) {
            enemyHealthBarFillElement.style.backgroundColor = 'yellow';
        } else if (enemyHpPercentage > 50) {
            enemyHealthBarFillElement.style.backgroundColor = '#baf508';
        }

        enemyHealthBarFillElement.style.width = enemyHpPercentage + '%';
        enemyHealthBarValueElement.textContent = enemy.currentHealth + '/' + enemy.health;

        if (enemyHpPercentage === 0) {
            attackBtn.textContent = 'Attack New Enemy'
        }
    }
})()


import {html, render} from 'https://unpkg.com/lit-html?module';

import {performGetRequest, performPostRequest} from '/js/utility/requests.js';

(function fightEnemy() {
    const heroHealthBarFillElement = document.getElementById('hero-health-bar-fill');
    const heroHealthBarValueElement = document.getElementById('hero-health-bar-value');
    const heroManaBarFillElement = document.getElementById('hero-mana-bar-fill');
    const heroManaBarValueElement = document.getElementById('hero-mana-bar-value');
    const enemyHealthBarFillElement = document.getElementById('enemy-health-bar-fill');
    const enemyHealthBarValueElement = document.getElementById('enemy-health-bar-value');
    const skillsElement = document.getElementById('skills');
    const attackBtn = document.getElementById('attack-btn');
    let firstBattle = true;

    document.getElementById('skills')
        .addEventListener('click', async (event) => {
            if (event.target.tagName === 'IMG' && attackBtn.textContent !== 'Attack Again' &&  attackBtn.textContent !== 'Try Again') {
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
            if (attackBtn.textContent === 'Attack Again' || attackBtn.textContent === 'Try Again') {
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
        const skills = [...hero.skillList];

        renderSkills(skills);

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

        if (hero.currentHealth === 0) {
            attackBtn.textContent = 'Try Again'
        }
    }

    function renderSkills(skills) {
        if (firstBattle) {
            skillsElement.innerHTML = '';
            firstBattle = !firstBattle;
        }

        const skillsTemplate = html`
                ${skills.map(setSkillView)}
        `;

        render(skillsTemplate, skillsElement);
    }

    function setSkillView(skill) {
        return html`
                <div class="col p-0">
                    <div class="skill-border bg-opacity-75 no-select">
                        <img src="${skill.imageUrl}" class="skill-icon ${skill.currentCoolDown != 0 ? ' inactive' : ''}" alt="skill-icon">
                    </div>
                    ${skill.currentCoolDown != 0 ?
                    html`<div if="" class="cool-down no-select text-secondary">${skill.currentCoolDown}</div>` : ''}
                    <p class="casted-skill hidden invisible">${skill.skillName}</p>
                    <p class="skill-dropdown text-secondary bg-primary bg-opacity-75 no-select align-self-center">Mana: ${skill.manaRequired} <br> ${skill.description}</p>
                </div>`;
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

        if (enemy.currentHealth === 0) {
            attackBtn.textContent = 'Attack Again'
        }
    }
})()


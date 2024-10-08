package jaina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import jaina.actions.ApplySorchedAction;
import jaina.modCore.IHelper;
import jaina.modCore.JainaEnums;
import jaina.powers.FrozenPower;
import jaina.powers.SorchedPower;

import java.util.List;

public abstract class AbstractFireCard extends AbstractJainaCard {

    private static final String SPELL_TYPE = IHelper.makeID("Fire");

    /**
     * 构造函数
     *
     * @param ID          完整的卡牌ID
     * @param useTestArt  是否使用测试图片
     * @param cardStrings 卡牌本地化字段
     * @param cost        能量花费（-1为X，-2不显示消耗）
     * @param type        卡牌类型
     * @param color       卡牌颜色
     * @param rarity      卡牌稀有度
     * @param target      卡牌目标
     */
    public AbstractFireCard(String ID, boolean useTestArt, CardStrings cardStrings, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(ID, useTestArt, cardStrings, cost, type, color, rarity, target, JainaEnums.CardTags.FIRE);
    }

    @Override
    public List<String> getCardDescriptors() {
        return getCardDescriptors(SPELL_TYPE);
    }

    public void dealFireDamage(AbstractPlayer p, AbstractMonster mo) {
        dealDamage(mo, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new ApplySorchedAction(p, mo, magicNumber));
    }

    public void calculateFireDamage(AbstractMonster mo) {
        int originalBaseDamage = this.baseDamage;

        if (mo.hasPower(SorchedPower.POWER_ID)) {
            this.baseDamage += mo.getPower(SorchedPower.POWER_ID).amount;
        }

        super.calculateCardDamage(mo);

        this.baseDamage = originalBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

        this.initializeDescription();
    }
}

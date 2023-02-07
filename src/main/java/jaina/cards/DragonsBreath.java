package jaina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jaina.modCore.IHelper;
import jaina.modCore.JainaEnums;


public class DragonsBreath extends AbstractJainaCard {

    public static final String ID = IHelper.makeID("DragonsBreath");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 2;

    public DragonsBreath() {
        super(ID, false, CARD_STRINGS, COST, CardType.ATTACK, JainaEnums.JAINA_COLOR,
                CardRarity.COMMON, CardTarget.ENEMY, JainaEnums.CardTags.FIRE);
        setDamage(12);
        setDamageType(JainaEnums.DamageType.FIRE);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    // 减费时显示金色框
    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (checkOnlyFire()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DragonsBreath();
    }

    // 以下都是需要检查是否减费的trigger
    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        // 打出【死灵诅咒】时无法减费
        if (!c.cardID.equals(Necronomicurse.ID)) changeCost();
    }

    @Override
    public void triggerWhenDrawn() {
        changeCost();
    }

    @Override
    public void triggerAtStartOfTurn() {
        changeCost();
    }

    @Override
    public void triggerOnManualDiscard() {
        changeCost();
    }

    @Override
    public void drawCards(int n) {
        super.drawCards(n);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        changeCost();
    }

    private boolean checkOnlyFire() {
        boolean onlyFire = true;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.hasTag(JainaEnums.CardTags.FIRE)) {
                onlyFire = false;
                break;
            }
        }
        return onlyFire;
    }

    private void changeCost() {
        if (checkOnlyFire() || isCostModifiedForTurn) {
            setCostForTurn(0);
        } else {
            setCostForTurn(cost);
            this.isCostModifiedForTurn = false;
        }
    }
}

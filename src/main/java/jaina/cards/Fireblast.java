package jaina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jaina.actions.unique.ApplyBurningAction;
import jaina.modCore.IHelper;
import jaina.modCore.JainaEnums;

public class Fireblast extends AbstractFireCard {
    public static final String ID = IHelper.makeID("Fireblast");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 0;

    public Fireblast() {
        super(ID, false, CARD_STRINGS, COST, CardType.ATTACK, JainaEnums.JAINA_COLOR,
                CardRarity.BASIC, CardTarget.ENEMY);
        setDamage(3);
        setMagicNumber(3);
    }

    @Override
    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new ApplyBurningAction(p, m, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fireblast();
    }
}

package jaina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

import jaina.actions.ApplySorchedAction;
import jaina.modCore.IHelper;
import jaina.modCore.JainaEnums;
import jaina.powers.SorchedPower;

public class Fireball extends AbstractFireCard {

    public static final String ID = IHelper.makeID("Fireball");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;

    public Fireball() {
        super(ID, false, CARD_STRINGS, COST, CardType.ATTACK, JainaEnums.JAINA_COLOR,
                CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(14);
        setMagicNumber(7);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new FireballEffect(p.hb_x, p.hb_y, m.hb_x, m.hb_y)));
        dealFireDamage(p, m);
    }        

    @Override
    public void upp() {
        upgradeDamage(6);
        upgradeMagicNumber(3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fireball();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        calculateFireDamage(mo);
    }
}

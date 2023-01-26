package Jaina.cards.optionCards;

import Jaina.ModCore.IHelper;
import Jaina.cards.Counterspell;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class CounterDamage extends OptionCard {
    private static final String ID = IHelper.makeID("CounterDamage");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public CounterDamage() {
        super(ID, new Counterspell(), CARD_STRINGS, CardType.POWER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        gainPower(new BufferPower(AbstractDungeon.player, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CounterDamage();
    }
}

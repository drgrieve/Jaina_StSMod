package jaina.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import jaina.modCore.IHelper;
import jaina.powers.unique.ExplosiveRunesPower;

public class SorchedPower extends AbstractJainaPower {
    public static final String POWER_ID = IHelper.makeID("SorchedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SorchedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, false, NAME, PowerType.DEBUFF);
        this.owner = owner;
        this.amount = amount;
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }


    @Override
    public void onInitialApplication() {
        addToBot(new VFXAction(new FireBurstParticleEffect(owner.hb.x, owner.hb.y)));
    }

    // 可以由外部触发
    @Override
    public void onSpecificTrigger() {
        if (!owner.isDying && !owner.isDeadOrEscaped()) {
            flash();
            addToBot(new DamageAction(owner, new DamageInfo(owner, amount, DamageInfo.DamageType.HP_LOSS),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }

    //  回合结束时自动触发
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new SorchedPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
   }

    @Override
    public void atStartOfTurn() {
        // if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        //     onSpecificTrigger();
        //     addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        // }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, amount);
    }

}

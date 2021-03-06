package com.hepolite.pangaea.skills;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.hepolite.pangaea.core.Manager;
import com.hepolite.pangaea.utility.SkillAPIHelper;
import com.hepolite.pillar.chat.Chat;
import com.sucy.skill.api.event.PlayerSkillDowngradeEvent;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerSkill;

public class SkillManager extends Manager
{
	private final HashMap<String, Skill> skills = new HashMap<String, Skill>();

	private int timer = 0;

	public SkillManager()
	{
		super(new SkillSettings());

		registerSkill(new SkillAbsorb());
		registerSkill(new SkillAdvancedBrewer());
		registerSkill(new SkillAmbientFeeding());
		registerSkill(new SkillAppleFarmer("Apple Farmer", Material.APPLE, 0.25f));
		registerSkill(new SkillAttitudeControl());
		registerSkill(new SkillAquaticLifeform());
		registerSkill(new SkillProduce("Bright Crafter"));
		registerSkill(new SkillDamageResistance("Bug"));
		registerSkill(new SkillCarefulSteps());
		registerSkill(new SkillCarry());
		registerSkill(new SkillCarrySeaPony());
		registerSkill(new SkillChangeWeather());
		registerSkill(new SkillProduce("Clear Resin"));
		registerSkill(new SkillCling());
		registerSkill(new SkillProduce("Cloud Production"));
		registerSkill(new SkillCloudSeed());
		registerSkill(new SkillProduce("Cooling"));
		registerSkill(new SkillCraftRespirator());
		registerSkill(new SkillCropExpert());
		registerSkill(new SkillDamageResistance("Crystallized"));
		registerSkill(new SkillDash());
		registerSkill(new SkillProduce("Deeper Understanding"));
		registerSkill(new SkillDeflectorShield());
		registerSkill(new SkillDimensionJump("Dimension Jump"));
		registerSkill(new SkillDimensionJump("Dimension Group Jump"));
		registerSkill(new SkillDisarm());
		registerSkill(new SkillDisguise());
		registerSkill(new SkillDistantInteraction());
		registerSkill(new SkillDistantReach());
		registerSkill(new SkillDrag());
		registerSkill(new SkillDurableArmor());
		registerSkill(new SkillEndurance());
		registerSkill(new SkillExperienced());
		registerSkill(new SkillHungerReduction("Efficient Flyer"));
		registerSkill(new SkillEnchanter());
		registerSkill(new SkillFarmer());
		registerSkill(new SkillMovement("Fast Swimmer"));
		registerSkill(new SkillFertilizing());
		registerSkill(new SkillFirePortal());
		registerSkill(new SkillFly("Fly"));
		registerSkill(new SkillFlyFaster());
		registerSkill(new SkillGatherer());
		registerSkill(new SkillAppleFarmer("Golden Apple Farmer", Material.GOLDEN_APPLE, 0.0f));
		registerSkill(new SkillGreenHooves());
		registerSkill(new SkillGrowTallTrees());
		registerSkill(new SkillGrowthExpertise());
		registerSkill(new SkillHailstorm());
		registerSkill(new SkillProduce("Harvest Sponge"));
		registerSkill(new SkillMovement("Haste"));
		registerSkill(new SkillMovement("Haste (Earth Pony)"));
		registerSkill(new SkillHighSpeedStrike());
		registerSkill(new SkillHold());
		registerSkill(new SkillHollowLeg());
		registerSkill(new SkillProduce("Infuse Stone"));
		registerSkill(new SkillImprovedFishing());
		registerSkill(new SkillKelpFarmer());
		registerSkill(new SkillCooldownIncrease("Lacerate"));
		registerSkill(new SkillLeap());
		registerSkill(new SkillLeapOfJoy());
		registerSkill(new SkillLeechGroup());
		registerSkill(new SkillHungerReduction("Long-Range Flyer"));
		registerSkill(new SkillLuckOfTheSea());
		registerSkill(new SkillMetabolism());
		registerSkill(new SkillNaturalConnection());
		registerSkill(new SkillNaturalEnvironment());
		registerSkill(new SkillNightProwler());
		registerSkill(new SkillNightVision());
		registerSkill(new SkillObservant());
		registerSkill(new SkillMovementCondition("Preen"));
		registerSkill(new SkillProduce("Prism"));
		registerSkill(new SkillQuickCatch());
		registerSkill(new SkillQuickRecovery());
		registerSkill(new SkillDamageResistance("Reinforced Shell"));
		registerSkill(new SkillDamageResistance("Resilient"));
		registerSkill(new SkillResourceful());
		registerSkill(new SkillRollingWithIt());
		registerSkill(new SkillScrollScribe());
		registerSkill(new SkillSeaCreature());
		registerSkill(new SkillSensing());
		registerSkill(new SkillProduce("Share the Love"));
		registerSkill(new SkillMovement("Soft Landing"));
		registerSkill(new SkillProduce("Solid Resin"));
		registerSkill(new SkillCooldownIncrease("Stab"));
		registerSkill(new SkillProduce("Sticky Resin"));
		registerSkill(new SkillDamageResistance("Sturdy"));
		registerSkill(new SkillMovementCondition("Swiftness"));
		registerSkill(new SkillSwipe());
		registerSkill(new SkillSwirl());
		registerSkill(new SkillTeamwork());
		registerSkill(new SkillTeleport("Teleport"));
		registerSkill(new SkillTeleport("Teleport Group"));
		registerSkill(new SkillTendAnimals());
		registerSkill(new SkillThaumicStrike());
		registerSkill(new SkillThunder());
		registerSkill(new SkillDamageResistance("Toughness"));
		registerSkill(new SkillTrained());
		registerSkill(new SkillProduce("Water Condensation"));
		registerSkill(new SkillWaterWorker());
	}

	@Override
	public void onTick()
	{
		timer++;
		updatePlayerVelocities();
		updateSkillTicking();

		if (timer % 12000 == 0)
			((SkillSettings) settings).saveSkillData();
	}

	/** Invoked whenever the server is shutting down */
	public void onShutdown()
	{
		((SkillSettings) settings).saveSkillData();
	}

	/** Stores a skill in the skill manager */
	public final void registerSkill(Skill skill)
	{
		if (skill != null)
			skills.put(skill.getName(), skill);
	}

	/** Returns the skill with the given name */
	public final Skill getSkill(String name)
	{
		return skills.get(name);
	}

	/** Returns the list of all the registered skill */
	public final Collection<Skill> getSkills()
	{
		return skills.values();
	}

	// //////////////////////////////////////////////////////////////

	private final HashMap<UUID, Vector> playerVelocities = new HashMap<UUID, Vector>();
	private final HashMap<UUID, Vector> playerOldVelocities = new HashMap<UUID, Vector>();
	private final HashMap<UUID, Integer> playerLastMoved = new HashMap<UUID, Integer>();

	/** Process players velocities */
	private final void updatePlayerVelocities()
	{
		List<UUID> playersToRemove = new LinkedList<UUID>();
		for (Entry<UUID, Vector> entry : playerVelocities.entrySet())
		{
			UUID uuid = entry.getKey();
			if (playerLastMoved.get(uuid) + 20 < timer)
				playersToRemove.add(uuid);
			if (playerVelocities.containsKey(uuid))
				playerOldVelocities.put(uuid, playerVelocities.get(uuid));
		}
		for (UUID uuid : playersToRemove)
		{
			playerVelocities.remove(uuid);
			playerOldVelocities.remove(uuid);
			playerLastMoved.remove(uuid);
		}
	}

	/** Used to compute the velocity of the players */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMoveEvent(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		Vector deltaPosition = event.getTo().clone().subtract(event.getFrom()).toVector();
		double deltaTime = timer - (playerLastMoved.containsKey(uuid) ? playerLastMoved.get(uuid) : timer);
		playerLastMoved.put(uuid, timer);
		if (deltaTime != 0.0)
		{
			playerVelocities.put(player.getUniqueId(), deltaPosition.multiply(1.0 / deltaTime));
			playerLastMoved.put(player.getUniqueId(), timer);
		}
	}

	/** Returns the velocity of a player, in blocks per tick */
	public Vector getPlayerVelocity(Player player)
	{
		if (playerOldVelocities.containsKey(player.getUniqueId()))
			return playerOldVelocities.get(player.getUniqueId());
		return new Vector(0.0, 0.0, 0.0);
	}

	// //////////////////////////////////////////////////////////////

	/** Updates all skills that needs to be ticked */
	private final void updateSkillTicking()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			PlayerClass race = SkillAPIHelper.getRace(player);
			for (PlayerSkill playerSkill : SkillAPIHelper.getSkills(player))
			{
				Skill skill = getSkill(playerSkill.getData().getName());
				if (skill != null && skill.getTickRate() != -1 && timer % skill.getTickRate() == 0)
					skill.onSkillTick(player, race, playerSkill, timer);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////

	private final HashMap<UUID, Integer> playerDowngradeTimes = new HashMap<UUID, Integer>();

	/** Prevents players from being able to downgrade their skills too often */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDowngradeSkill(PlayerSkillDowngradeEvent event)
	{
		Player player = event.getPlayerData().getPlayer();

		int wait = settings.getInt("General.downgradeWaitTime");
		int last = getPlayerLastDowngrade(player);
		int left = (last == -1 ? 0 : wait - (timer - last));
		if (left > 0)
		{
			Chat.message(player, String.format("&cYou may not downgrade another skill for another &6%d&c minutes", 1 + left / (20 * 60)));
			event.setCancelled(true);
		}
		else
			setPlayerLastDowngrade(player);
	}

	/** Returns when the given player last downgraded a skill */
	private final int getPlayerLastDowngrade(Player player)
	{
		UUID uuid = player.getUniqueId();
		return playerDowngradeTimes.containsKey(uuid) ? playerDowngradeTimes.get(uuid) : -1;
	}

	/** Sets the player's last downgrade to the current time */
	private final void setPlayerLastDowngrade(Player player)
	{
		playerDowngradeTimes.put(player.getUniqueId(), timer);
	}
}

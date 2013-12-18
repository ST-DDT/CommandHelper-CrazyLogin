package de.st_ddt.crazy.commandhelper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.laytonsmith.annotations.api;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import de.st_ddt.crazylogin.CrazyLogin;

public class CHCrazyLogin
{

	@startup
	public static void onEnable()
	{
		try
		{
			Static.checkPlugin("CrazyCore", Target.UNKNOWN);
		}
		catch (final Exception e)
		{
			System.out.println("[CommandHelper] CHCrazyLogin Could not find CrazyCore please make sure you have it installed.");
		}
		try
		{
			Static.checkPlugin("CrazyLogin", Target.UNKNOWN);
		}
		catch (final Exception e)
		{
			System.out.println("[CommandHelper] CHCrazyLogin Could not find CrazyLogin please make sure you have it installed.");
		}
		System.out.println("[CommandHelper] CHCrazyLogin Initialized - Crazy");
	}

	@api
	public static class crazylogin_is_registered extends AbstractFunction
	{

		public String getName()
		{
			return "crazylogin_is_registered";
		}

		public Integer[] numArgs()
		{
			return new Integer[] { 1 };
		}

		public Construct exec(final Target t, final Environment env, final Construct... args) throws ConfigRuntimeException
		{
			Static.checkPlugin("CrazyLogin", t);
			final CrazyLogin login = CrazyLogin.getPlugin();
			if (args.length != 1)
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
			else if (args[0] instanceof CString)
				return new CBoolean(login.hasPlayerData(args[0].val()), t);
			else
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
		}

		public ExceptionType[] thrown()
		{
			return new ExceptionType[] { ExceptionType.InvalidPluginException };
		}

		public boolean isRestricted()
		{
			return true;
		}

		public Boolean runAsync()
		{
			return null;
		}

		public String docs()
		{
			return "boolean {playername} returns true if the player is registered.";
		}

		public CHVersion since()
		{
			return CHVersion.V3_3_1;
		}
	}

	@api
	public static class crazylogin_is_loggedIn extends AbstractFunction
	{

		public String getName()
		{
			return "crazylogin_is_loggedIn";
		}

		public Integer[] numArgs()
		{
			return new Integer[] { 1 };
		}

		public Construct exec(final Target t, final Environment env, final Construct... args) throws ConfigRuntimeException
		{
			Static.checkPlugin("CrazyLogin", t);
			final CrazyLogin login = CrazyLogin.getPlugin();
			if (args.length != 1)
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
			else if (args[0] instanceof CString)
			{
				final Player player = Bukkit.getPlayerExact(args[0].val());
				if (player == null)
					return new CBoolean(false, t);
				else
					return new CBoolean(login.isLoggedIn(player), t);
			}
			else
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
		}

		public ExceptionType[] thrown()
		{
			return new ExceptionType[] { ExceptionType.InvalidPluginException };
		}

		public boolean isRestricted()
		{
			return true;
		}

		public Boolean runAsync()
		{
			return false;
		}

		public String docs()
		{
			return "boolean {playername} returns true if the player is logged in.";
		}

		public CHVersion since()
		{
			return CHVersion.V3_3_1;
		}
	}
}

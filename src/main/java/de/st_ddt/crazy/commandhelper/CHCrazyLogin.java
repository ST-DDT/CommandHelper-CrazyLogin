package de.st_ddt.crazy.commandhelper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import de.st_ddt.crazylogin.CrazyLogin;
import de.st_ddt.crazylogin.data.LoginPlayerData;

@MSExtension("CHCrazyLogin")
public class CHCrazyLogin extends AbstractExtension
{

	public void onEnable()
	{
		try
		{
			Static.checkPlugin("CrazyCore", Target.UNKNOWN);
		}
		catch (final Exception e)
		{
			System.err.println("[CommandHelper] CHCrazyLogin Could not find CrazyCore please make sure you have it installed.");
		}
		try
		{
			Static.checkPlugin("CrazyLogin", Target.UNKNOWN);
		}
		catch (final Exception e)
		{
			System.err.println("[CommandHelper] CHCrazyLogin Could not find CrazyLogin please make sure you have it installed.");
		}
		System.out.println("[CommandHelper] CHCrazyLogin Initialized - Crazy");
	}

	@Override
	public void onShutdown()
	{
		System.out.println("[CommandHelper] CHCrazyLogin Shutdown - Crazy");
	}

	@Override
	public Version getVersion()
	{
		return new SimpleVersion(1, 2, 0);
	}

	@api
	public static class crazylogin_is_registered extends AbstractFunction
	{

		@Override
		public String getName()
		{
			return "crazylogin_is_registered";
		}

		@Override
		public Integer[] numArgs()
		{
			return new Integer[] { 1 };
		}

		@Override
		public Construct exec(final Target t, final Environment env, final Construct... args) throws ConfigRuntimeException
		{
			Static.checkPlugin("CrazyLogin", t);
			final CrazyLogin login = CrazyLogin.getPlugin();
			if (args.length != 1)
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
			else if (args[0] instanceof CString)
				return CBoolean.GenerateCBoolean(login.hasPlayerData(args[0].val()), t);
			else
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
		}

		@Override
		public ExceptionType[] thrown()
		{
			return new ExceptionType[] { ExceptionType.InvalidPluginException };
		}

		@Override
		public boolean isRestricted()
		{
			return true;
		}

		@Override
		public Boolean runAsync()
		{
			return null;
		}

		@Override
		public String docs()
		{
			return "boolean {playername} returns true if the player is registered.";
		}

		@Override
		public CHVersion since()
		{
			return CHVersion.V3_3_1;
		}
	}

	@api
	public static class crazylogin_is_loggedIn extends AbstractFunction
	{

		@Override
		public String getName()
		{
			return "crazylogin_is_loggedIn";
		}

		@Override
		public Integer[] numArgs()
		{
			return new Integer[] { 1 };
		}

		@Override
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
					return CBoolean.GenerateCBoolean(false, t);
				else
					return CBoolean.GenerateCBoolean(login.isLoggedIn(player), t);
			}
			else
				throw new ConfigRuntimeException("Invalid arguments. Use playername", ExceptionType.FormatException, t);
		}

		@Override
		public ExceptionType[] thrown()
		{
			return new ExceptionType[] { ExceptionType.InvalidPluginException };
		}

		@Override
		public boolean isRestricted()
		{
			return true;
		}

		@Override
		public Boolean runAsync()
		{
			return false;
		}

		@Override
		public String docs()
		{
			return "boolean {playername} returns true if the player is logged in.";
		}

		@Override
		public CHVersion since()
		{
			return CHVersion.V3_3_1;
		}
	}

	@api
	public static class crazylogin_check_password extends AbstractFunction
	{

		@Override
		public String getName()
		{
			return "crazylogin_check_password";
		}

		@Override
		public Integer[] numArgs()
		{
			return new Integer[] { 2 };
		}

		@Override
		public Construct exec(final Target t, final Environment env, final Construct... args) throws ConfigRuntimeException
		{
			Static.checkPlugin("CrazyLogin", t);
			final CrazyLogin login = CrazyLogin.getPlugin();
			if (args.length != 2)
				throw new ConfigRuntimeException("Invalid arguments. Use playername password", ExceptionType.FormatException, t);
			else if (args[0] instanceof CString && args[1] instanceof CString)
			{
				final LoginPlayerData data = login.getPlayerData(((CString) args[0]).val());
				if (data == null)
					return CBoolean.GenerateCBoolean(false, t);
				else
					return CBoolean.GenerateCBoolean(data.isPassword(((CString) args[1]).val()), t);
			}
			else
				throw new ConfigRuntimeException("Invalid arguments. Use playername password", ExceptionType.FormatException, t);
		}

		@Override
		public ExceptionType[] thrown()
		{
			return new ExceptionType[] { ExceptionType.InvalidPluginException };
		}

		@Override
		public boolean isRestricted()
		{
			return true;
		}

		@Override
		public Boolean runAsync()
		{
			return null;
		}

		@Override
		public String docs()
		{
			return "boolean {playername, password} returns true if the player's password matches the given password.";
		}

		@Override
		public CHVersion since()
		{
			return CHVersion.V3_3_1;
		}
	}
}

package pwe.planner.logic.parser;

import static java.util.Objects.requireNonNull;
import static pwe.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_CODE;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_YEAR;
import static pwe.planner.logic.parser.ParserUtil.arePrefixesPresent;
import static pwe.planner.logic.parser.ParserUtil.parseCodes;
import static pwe.planner.logic.parser.ParserUtil.parseSemester;
import static pwe.planner.logic.parser.ParserUtil.parseYear;

import java.util.Set;

import pwe.planner.logic.commands.PlannerAddCommand;
import pwe.planner.logic.parser.exceptions.ParseException;
import pwe.planner.model.module.Code;
import pwe.planner.model.planner.Semester;
import pwe.planner.model.planner.Year;

/**
 * Parses input arguments and creates a new PlannerAddCommand object.
 */
public class PlannerAddCommandParser implements Parser<PlannerAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlannerAddCommand
     * and returns an PlannerAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public PlannerAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isEmpty()) {
            throw new ParseException(PlannerAddCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_YEAR , PREFIX_SEMESTER);

        if (!arePrefixesPresent(argMultimap, PREFIX_CODE, PREFIX_YEAR , PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlannerAddCommand.MESSAGE_USAGE));
        }

        Set<Code> codes = parseCodes(argMultimap.getAllValues(PREFIX_CODE));
        Year year = parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Semester semester = parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());

        return new PlannerAddCommand(year, semester, codes);
    }
}

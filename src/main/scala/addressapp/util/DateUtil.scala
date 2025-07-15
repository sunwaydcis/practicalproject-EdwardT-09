package addressapp.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtil:
  val DATE_PATTERN = "dd.MM.yyyy"
  val DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)
  extension (date:LocalDate)
  //    returns the given date as a well formatted String
  //    The above defined {@link DateUtil#DATE_PATTERN} is used.
  //
  //    @param date the date to be returned as a string
  //    @return formatted string

    def asString:String =
      if(date == null)
        return null
      return DATE_FORMATTER.format(date)

  extension (data:String)
  //    converts a String in the format of teh defined
  //    {@link DateUtil#DATE_PATTERN} to a {@link LocalDate} object.
  //
  //    returns null if the string could not be converted
  //
  //    @param dateString the date as String
  //    @return the date object (or null if conversion impossible)

    def parseLocalDate: LocalDate =
      try
        LocalDate.parse(data, DATE_FORMATTER)
      catch
        case e: DateTimeParseException => null

    def isValid:Boolean =
      data.parseLocalDate != null
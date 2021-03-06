package com.akqa.booking.components.helpers;

import com.akqa.booking.exceptions.BookingException;
import com.akqa.test.constants.TestCommonConstants;
import com.akqa.test.helpers.TestBookingBuilderHelper;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.then;
import static com.googlecode.catchexception.apis.CatchExceptionBdd.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.batch.test.AssertFile.assertFileEquals;

public class BookingCalendarHelperTest {

    private final BookingCalendarHelper bookingCalendarHelper = new BookingCalendarHelper();

    @After
    public void afterTest() throws IOException {
        FileUtils.fileDelete(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendar_thenSuccess()
            throws Exception {
        bookingCalendarHelper.createOutputFileFromBookingCalendar(
                TestBookingBuilderHelper.bookingCalendarBuilder(),
                TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File outputFile = new File(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File expectedOutputFile = new File(
                TestCommonConstants.EXPECTED_HELPER_OUTPUT_FILE_NAME_AND_PATH);
        assertThat(outputFile, is(not(nullValue())));
        assertFileEquals(expectedOutputFile, outputFile);
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendarAndNullCalendar_thenSuccess()
            throws Exception {
        bookingCalendarHelper.createOutputFileFromBookingCalendar(null,
                TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        File outputFile = new File(TestCommonConstants.HELPER_OUTPUT_FILE_NAME_AND_PATH);
        assertThat(outputFile.exists(), equalTo(false));
    }

    @Test
    public void whenICreateOutputFileFromBookingCalendarAndOutputDirectoryDoesNotExist_thenException()
            throws Exception {
        when(bookingCalendarHelper).createOutputFileFromBookingCalendar(
                TestBookingBuilderHelper.bookingCalendarBuilder(),
                TestCommonConstants.FILE_NAME_AND_NON_EXISTENT_PATH);
        then(caughtException())
                .isInstanceOf(BookingException.class)
                .hasMessageContaining(
                        TestCommonConstants.NON_EXISTENT_DIRECTORY_ERROR_MESSAGE);
    }
}

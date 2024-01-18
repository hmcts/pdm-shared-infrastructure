package uk.gov.hmcts.pdm.publicdisplay.manager.web.cdus;

import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.easymock.IAnswer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.validation.BindingResult;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;

/**
 * The Class CduExpectValidators.
 *
 * @author harrism
 */
@ExtendWith(EasyMockExtension.class)
abstract class CduExpectValidators extends CduControllerTestInitialiser {

    /**
     * Expect cdu search selected validator.
     *
     * @param capturedSearchCommand the captured search command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectCduSearchSelectedValidator(
        final Capture<CduSearchCommand> capturedSearchCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockCduSearchSelectedValidator.validate(capture(capturedSearchCommand),
            capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockCduSearchSelectedValidator);
    }

    /**
     * Expect cdu restart all validator.
     *
     * @param capturedSearchCommand the captured search command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectCduRestartAllValidator(
        final Capture<CduSearchCommand> capturedSearchCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockCduRestartAllValidator.validate(capture(capturedSearchCommand),
            capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockCduRestartAllValidator);
    }

    /**
     * Expect cdu amend validator.
     *
     * @param capturedCduCommand the captured cdu command
     * @param capturedErrors the captured errors
     * @param isSuccess the is success
     */
    protected void expectCduAmendValidator(final Capture<CduAmendCommand> capturedCduCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockCduAmendValidator.validate(capture(capturedCduCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockCduAmendValidator);
    }

    /**
     * Expect cdu search validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectCduSearchValidator(final Capture<CduSearchCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockCduSearchValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockCduSearchValidator);
    }

    /**
     * Expect unregister cdu validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectUnregisterCduValidator(final Capture<CduSearchCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockCduUnregisterValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockCduUnregisterValidator);
    }

    /**
     * Expect mapping add validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectMappingAddValidator(final Capture<MappingCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockMappingAddValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockMappingAddValidator);
    }

    /**
     * Expect mapping remove validator.
     *
     * @param capturedCommand the captured command
     * @param capturedErrors the captured errors
     * @param isSuccess the validator response
     */
    protected void expectMappingRemoveValidator(final Capture<MappingCommand> capturedCommand,
        final Capture<BindingResult> capturedErrors, final boolean isSuccess) {
        mockMappingRemoveValidator.validate(capture(capturedCommand), capture(capturedErrors));
        expectValidatorSuccess(isSuccess);
        replay(mockMappingRemoveValidator);
    }

    /**
     * Expect validator success.
     *
     * @param isSuccess the is success
     */
    protected void expectValidatorSuccess(final boolean isSuccess) {
        if (isSuccess) {
            expectLastCall();
        } else {
            expectLastCall().andAnswer(new IAnswer<Void>() {
                @Override
                public Void answer() {
                    ((BindingResult) getCurrentArguments()[1]).reject("mock error message");
                    return null;
                }
            });
        }
    }
}

package uk.gov.hmcts.pdm.publicdisplay.manager.service;

import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.hmcts.pdm.publicdisplay.common.exception.RestException;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CduJson;
import uk.gov.hmcts.pdm.publicdisplay.common.json.CduStatusJson;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonRequest;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonRequestFactory;
import uk.gov.hmcts.pdm.publicdisplay.common.rest.JsonWebTokenType;
import uk.gov.hmcts.pdm.publicdisplay.common.test.AbstractJUnit;
import uk.gov.hmcts.pdm.publicdisplay.common.util.AppConstants;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.CduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.LocalProxy;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICduModel;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ICourtSite;
import uk.gov.hmcts.pdm.publicdisplay.manager.domain.api.ILocalProxy;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

/**
 * The Class LocalProxyJsonRequest.
 */
@ExtendWith(EasyMockExtension.class)
abstract class LocalProxyJsonRequest extends AbstractJUnit {

    protected static final String CDU_NUMBER = "CDUNO";

    /** The Constant IPADDRESS. */
    protected static final String IPADDRESS_PREFIX = "127.0.0.";

    /** The Constant MACADDRESS. */
    protected static final String MACADDRESS_PREFIX = "MACADDRESS";

    /** The Constant TIMEOUT. */
    protected static final Integer TIMEOUT = 10;

    /** The Constant EXPIRY. */
    protected static final Integer EXPIRY = 30;

    protected static final String MOCK_REST_EXCEPTION = "Mock rest exception";

    /** The mock json request factory. */
    protected JsonRequestFactory mockJsonRequestFactory;

    /** The mock json request. */
    protected JsonRequest mockJsonRequest;

    /**
     * Gets the test cdu json.
     *
     * @param id the id
     * @return the test cdu json
     */
    protected CduJson getTestCduJson(final int id) {
        final CduJson cduJson = new CduJson();
        cduJson.setCduNumber(CDU_NUMBER + id);
        cduJson.setIpAddress(IPADDRESS_PREFIX + id);
        cduJson.setMacAddress(MACADDRESS_PREFIX + id);
        cduJson.setRagStatus(id % 2 == 0 ? AppConstants.GREEN_CHAR : AppConstants.RED_CHAR);
        return cduJson;
    }

    /**
     * Gets the test local proxy.
     *
     * @return the test local proxy
     */
    protected ILocalProxy getTestLocalProxy() {
        final ILocalProxy testLocalProxy = new LocalProxy();
        testLocalProxy.setId(1L);
        testLocalProxy.setIpAddress(IPADDRESS_PREFIX + 100);
        return testLocalProxy;
    }

    /**
     * Gets the test cdu status jsons.
     *
     * @param cduJsons the cdu jsons
     * @return the test cdu status jsons
     */
    protected List<CduStatusJson> getTestCduStatusJsons(final List<CduJson> cduJsons) {
        final List<CduStatusJson> cduStatusJsons = new ArrayList<>();
        for (CduJson cduJson : cduJsons) {
            final CduStatusJson cduStatusJson = createCduStatusJson();
            cduStatusJson.setMacAddress(cduJson.getMacAddress());
            cduStatusJson.setRagStatus(cduJson.getRagStatus());
            cduStatusJsons.add(cduStatusJson);
        }
        return cduStatusJsons;
    }

    private CduStatusJson createCduStatusJson() {
        return new CduStatusJson();
    }

    /**
     * Gets the test cdu jsons.
     *
     * @return the test cdu jsons
     */
    protected CduJson[] getTestCduJsons() {
        final CduJson[] cduJsons = new CduJson[5];
        for (int arrayNo = 0; arrayNo < cduJsons.length; arrayNo++) {
            cduJsons[arrayNo] = getTestCduJson(arrayNo);
        }
        return cduJsons;
    }

    /**
     * Gets the test cdu.
     *
     * @param courtSite the court site
     * @return the test cdu
     */
    protected ICduModel getTestCdu(final ICourtSite courtSite) {
        final ICduModel testCdu = new CduModel();
        testCdu.setId(1L);
        testCdu.setCourtSite(courtSite);
        testCdu.setCduNumber(CDU_NUMBER + testCdu.getId());
        testCdu.setTitle("TEST_CDU" + testCdu.getId());
        testCdu.setDescription("TEST_CDU" + testCdu.getId());
        testCdu.setLocation("TEST_LOCATION");
        testCdu.setRefresh(30L);
        testCdu.setIpAddress(IPADDRESS_PREFIX + testCdu.getId());
        testCdu.setMacAddress(MACADDRESS_PREFIX + testCdu.getId());
        testCdu.setOfflineIndicator('Y');
        return testCdu;
    }

    /**
     * Mock json request with capture.
     *
     * @param <T> the generic type
     * @param path the path
     * @param capturedJsonRequest the captured json request
     */
    protected <T> void mockJsonRequestWithCapture(final String path,
        final Capture<T> capturedJsonRequest) {
        expect(mockJsonRequestFactory.getJsonRequest()).andReturn(mockJsonRequest);
        replay(mockJsonRequestFactory);
        mockCreateJsonRequest(path);
        mockJsonRequest.sendRequest(capture(capturedJsonRequest));
        expectLastCall();
        replay(mockJsonRequest);
    }

    /**
     * Mock json request with exception.
     *
     * @param <T> the generic type
     * @param path the path
     * @param capturedJsonRequest the captured json request
     */
    protected <T> void mockJsonRequestWithException(final String path,
        final Capture<T> capturedJsonRequest) {
        expect(mockJsonRequestFactory.getJsonRequest()).andReturn(mockJsonRequest);
        replay(mockJsonRequestFactory);
        mockCreateJsonRequest(path);
        mockJsonRequest.sendRequest(capture(capturedJsonRequest));
        expectLastCall().andThrow(new RestException(MOCK_REST_EXCEPTION));
        replay(mockJsonRequest);
    }

    /**
     * Mock create json request.
     *
     * @param path the path
     */
    protected void mockCreateJsonRequest(final String path) {
        mockJsonRequest.setTokenType(JsonWebTokenType.DISPLAY_MANAGER);
        expectLastCall();
        mockJsonRequest.setTimeout(TIMEOUT);
        expectLastCall();
        mockJsonRequest.setExpiry(EXPIRY);
        expectLastCall();
        mockJsonRequest
            .setUrl("http://" + getTestLocalProxy().getIpAddress() + "/ipdmanager/api" + path);
        expectLastCall();
    }
}

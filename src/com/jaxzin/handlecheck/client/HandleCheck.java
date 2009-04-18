package com.jaxzin.handlecheck.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class HandleCheck implements EntryPoint {

    private HorizontalPanel inputPanel = new HorizontalPanel();
    private VerticalPanel inputAndResultsPanel = new VerticalPanel();

    private TextBox entryBox = new TextBox();
    private Button checkButton = new Button("Check");
    private FlexTable results = new FlexTable();

    private HandleCheckServiceAsync service;

    public void onModuleLoad() {

        service = HandleCheckService.App.getInstance();

        int row = 0;
        for (HandleProvider provider : HandleProvider.values()) {
            results.setText(row++, 0, provider.getDisplayName());
        }

        inputPanel.add(entryBox);
        inputPanel.add(checkButton);

        inputAndResultsPanel.add(inputPanel);
        inputAndResultsPanel.add(results);

        RootPanel.get("handleCheck").add(inputAndResultsPanel);

        entryBox.setFocus(true);


        checkButton.addClickHandler(
                new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        // Call service for each
                        for (HandleProvider provider : HandleProvider.values()) {
                            results.setText(provider.ordinal(), 1, "Checking...");
                            service.checkHandle(entryBox.getText(), provider,
                                    new AsyncCallback<HandleCheckResult>() {
                                        public void onFailure(Throwable caught) {
                                            try {
                                                throw caught;
                                            } catch(HandleCheckException e) {
                                                results.setText(e.getProvider().ordinal(), 1, "Error: " + (e.getCause() != null ? e.getCause().getMessage() : e.getMessage()));
                                            } catch(Throwable e) {
                                                // todo: display error
                                            }
                                        }

                                        public void onSuccess(HandleCheckResult result) {
                                            results.setText(result.getProvider().ordinal(), 1, result.isAvailable() ? "Available" : "Taken");
                                        }
                                    }
                            );
                        }
                    }
                }
        );

        // Pressing Enter will trigger the "Check" button
        entryBox.addKeyDownHandler(
                new KeyDownHandler() {
                    public void onKeyDown(KeyDownEvent event) {
                        if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                            checkButton.click();
                        }
                    }
                }
        );
        
    }
}

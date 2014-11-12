package com.zeroclue.jmeter.protocol.amqp.gui;

import com.zeroclue.jmeter.protocol.amqp.AMQPSamplerSSL;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import javax.swing.*;
import java.awt.*;

public abstract class AMQPSamplerGuiSSL extends AbstractSamplerGui {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggingManager.getLoggerForClass();

    protected JLabeledTextField sslKeyStore = new JLabeledTextField("Key Store Path");
    protected JLabeledTextField sslKeyStorePass = new JLabeledTextField("Key Store Password");
    protected JLabeledTextField sslTrustStore = new JLabeledTextField("Trust Store Path");
    protected JLabeledTextField sslTrustStorePass = new JLabeledTextField("Trust Store Password");
    protected JLabeledTextField sslUserId = new JLabeledTextField("SSL Cert CN");


    protected JLabeledTextField exchange = new JLabeledTextField("Exchange");
    private final JCheckBox exchangeRedeclare = new JCheckBox("Redeclare?", AMQPSamplerSSL.DEFAULT_EXCHANGE_REDECLARE);
    private final JCheckBox exchangeDeclarePassive = new JCheckBox("Declare Passive?", AMQPSamplerSSL.DEFAULT_EXCHANGE_DECLARE_PASSIVE);
    protected JLabeledTextField queue = new JLabeledTextField("Queue");
    protected JLabeledTextField routingKey = new JLabeledTextField("Routing Key");
    protected JLabeledTextField virtualHost = new JLabeledTextField("Virtual Host");
    protected JLabeledTextField messageTTL = new JLabeledTextField("Message TTL");
    protected JLabeledTextField messageExpires = new JLabeledTextField("Expires");
    protected JLabeledChoice exchangeType = new JLabeledChoice("Exchange Type", new String[]{"direct", "topic", "headers", "fanout"});
    private final JCheckBox exchangeDurable = new JCheckBox("Durable?", AMQPSamplerSSL.DEFAULT_EXCHANGE_DURABLE);
    private final JCheckBox queueDurable = new JCheckBox("Durable?", true);
    private final JCheckBox queueRedeclare = new JCheckBox("Redeclare?", AMQPSamplerSSL.DEFAULT_QUEUE_REDECLARE);
    private final JCheckBox queueDeclarePassive = new JCheckBox("Declare Passive?", AMQPSamplerSSL.DEFAULT_QUEUE_DECLARE_PASSIVE);
    private final JCheckBox queueExclusive = new JCheckBox("Exclusive", true);
    private final JCheckBox queueAutoDelete = new JCheckBox("Auto Delete?", true);

    protected JLabeledTextField host = new JLabeledTextField("Host");
    protected JLabeledTextField port = new JLabeledTextField("Port");
    protected JLabeledTextField timeout = new JLabeledTextField("Timeout");
    protected JLabeledTextField username = new JLabeledTextField("Username");
    protected JLabeledTextField password = new JLabeledTextField("Password");
    private final JCheckBox SSL = new JCheckBox("SSL?", false);

    private final JLabeledTextField iterations = new JLabeledTextField("Number of samples to Aggregate");


    protected abstract void setMainPanel(JPanel panel);

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (!(element instanceof AMQPSamplerSSL)) return;
        AMQPSamplerSSL sampler = (AMQPSamplerSSL) element;

        sslKeyStore.setText(sampler.getsslKeyStore());
        sslKeyStorePass.setText(sampler.getsslKeyStorePass());

        sslTrustStore.setText(sampler.getsslTrustStore());
        sslTrustStorePass.setText(sampler.getsslTrustStorePass());

        sslUserId.setText(sampler.getSslUserId());

        exchange.setText(sampler.getExchange());
        exchangeType.setText(sampler.getExchangeType());
        exchangeDurable.setSelected(sampler.getExchangeDurable());
        exchangeRedeclare.setSelected(sampler.getExchangeRedeclare());
        exchangeDeclarePassive.setSelected(sampler.getExchangeDeclarePassive());
        queue.setText(sampler.getQueue());
        routingKey.setText(sampler.getRoutingKey());
        virtualHost.setText(sampler.getVirtualHost());
        messageTTL.setText(sampler.getMessageTTL());
        messageExpires.setText(sampler.getMessageExpires());
        queueDurable.setSelected(sampler.queueDurable());
        queueExclusive.setSelected(sampler.queueExclusive());
        queueAutoDelete.setSelected(sampler.queueAutoDelete());
        queueRedeclare.setSelected(sampler.getQueueRedeclare());
        queueDeclarePassive.setSelected(sampler.getQueueDeclarePassive());

        timeout.setText(sampler.getTimeout());
        iterations.setText(sampler.getIterations());

        host.setText(sampler.getHost());
        port.setText(sampler.getPort());
        username.setText(sampler.getUsername());
        password.setText(sampler.getPassword());
        SSL.setSelected(sampler.connectionSSL());
        log.info("AMQPSamplerGuiSSL.configure() called");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGui() {

        sslKeyStore.setText("");
        sslKeyStorePass.setText("");
        sslTrustStore.setText("");
        sslTrustStorePass.setText("");
        sslUserId.setText("");

        exchange.setText("jmeterExchange");
        queue.setText("jmeterQueue");
        exchangeDurable.setSelected(AMQPSamplerSSL.DEFAULT_EXCHANGE_DURABLE);
        exchangeRedeclare.setSelected(AMQPSamplerSSL.DEFAULT_EXCHANGE_REDECLARE);
        exchangeDeclarePassive.setSelected(AMQPSamplerSSL.DEFAULT_EXCHANGE_DECLARE_PASSIVE);
        routingKey.setText("jmeterRoutingKey");
        virtualHost.setText("/");
        messageTTL.setText("");
        messageExpires.setText("");
        exchangeType.setText("direct");
        queueDurable.setSelected(true);
        queueExclusive.setSelected(false);
        queueAutoDelete.setSelected(false);
        queueRedeclare.setSelected(AMQPSamplerSSL.DEFAULT_QUEUE_REDECLARE);
        queueDeclarePassive.setSelected(AMQPSamplerSSL.DEFAULT_QUEUE_DECLARE_PASSIVE);


        timeout.setText(AMQPSamplerSSL.DEFAULT_TIMEOUT_STRING);
        iterations.setText(AMQPSamplerSSL.DEFAULT_ITERATIONS_STRING);

        host.setText("localhost");
        port.setText(AMQPSamplerSSL.DEFAULT_PORT_STRING);
        username.setText("guest");
        password.setText("guest");
        SSL.setSelected(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyTestElement(TestElement element) {
        AMQPSamplerSSL sampler = (AMQPSamplerSSL) element;
        sampler.clear();
        configureTestElement(sampler);

        sampler.setSslKeystore(sslKeyStore.getText());
        sampler.setsslKeyStorePass(sslKeyStorePass.getText());
        sampler.setsslTrustStore(sslTrustStore.getText());
        sampler.setSslTruststorePass(sslTrustStorePass.getText());
        sampler.setSslUserId(sslUserId.getText());


        sampler.setExchange(exchange.getText());
        sampler.setExchangeDurable(exchangeDurable.isSelected());
        sampler.setExchangeRedeclare(exchangeRedeclare.isSelected());
        sampler.setExchangeDeclarePassive(exchangeDeclarePassive.isSelected());
        sampler.setQueue(queue.getText());
        sampler.setRoutingKey(routingKey.getText());
        sampler.setVirtualHost(virtualHost.getText());
        sampler.setMessageTTL(messageTTL.getText());
        sampler.setMessageExpires(messageExpires.getText());
        sampler.setExchangeType(exchangeType.getText());
        sampler.setQueueDurable(queueDurable.isSelected());
        sampler.setQueueExclusive(queueExclusive.isSelected());
        sampler.setQueueAutoDelete(queueAutoDelete.isSelected());
        sampler.setQueueRedeclare(queueRedeclare.isSelected());
        sampler.setQueueDeclarePassive(queueDeclarePassive.isSelected());

        sampler.setTimeout(timeout.getText());
        sampler.setIterations(iterations.getText());

        sampler.setHost(host.getText());
        sampler.setPort(port.getText());
        sampler.setUsername(username.getText());
        sampler.setPassword(password.getText());
        sampler.setConnectionSSL(SSL.isSelected());
        log.info("AMQPSamplerGuiSSL.modifyTestElement() called, set user/pass to " + username.getText() + "/" + password.getText() + " on sampler " + sampler);
    }

    protected void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

        JPanel mainPanel = new VerticalPanel();

        mainPanel.add(makeCommonPanel());

        iterations.setPreferredSize(new Dimension(50, 25));
        mainPanel.add(iterations);

        add(mainPanel);

        setMainPanel(mainPanel);
    }

    private Component makeCommonPanel() {
        GridBagConstraints gridBagConstraints, gridBagConstraintsCommon;

        gridBagConstraintsCommon = new GridBagConstraints();
        gridBagConstraintsCommon.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsCommon.anchor = GridBagConstraints.WEST;
        gridBagConstraintsCommon.weightx = 0.5;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;

        JPanel commonPanel = new JPanel(new GridBagLayout());

        JPanel sslSettings = new JPanel(new GridBagLayout());
        sslSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "SSL"));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        sslSettings.add(sslKeyStore, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        sslSettings.add(sslKeyStorePass, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        sslSettings.add(sslTrustStore, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        sslSettings.add(sslTrustStorePass, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        sslSettings.add(sslUserId, gridBagConstraints);


        JPanel exchangeSettings = new JPanel(new GridBagLayout());
        exchangeSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Exchange"));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        exchangeSettings.add(exchange, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        exchangeSettings.add(exchangeType, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        exchangeSettings.add(exchangeDurable, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        exchangeSettings.add(exchangeRedeclare, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        exchangeSettings.add(exchangeDeclarePassive, gridBagConstraints);

        JPanel queueSettings = new JPanel(new GridBagLayout());
        queueSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Queue"));


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        queueSettings.add(queue, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        queueSettings.add(routingKey, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        queueSettings.add(messageTTL, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        queueSettings.add(messageExpires, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        queueSettings.add(queueDurable, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        queueSettings.add(queueExclusive, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        queueSettings.add(queueAutoDelete, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        queueSettings.add(queueRedeclare, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        queueSettings.add(queueDeclarePassive, gridBagConstraints);

        gridBagConstraintsCommon.gridx = 0;
        gridBagConstraintsCommon.gridy = 0;

        JPanel exchangeQueueSettings = new VerticalPanel();
        exchangeQueueSettings.add(sslSettings);
        exchangeQueueSettings.add(exchangeSettings);
        exchangeQueueSettings.add(queueSettings);

        commonPanel.add(exchangeQueueSettings, gridBagConstraintsCommon);


        JPanel serverSettings = new JPanel(new GridBagLayout());
        serverSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection"));

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        serverSettings.add(virtualHost, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        serverSettings.add(host, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        serverSettings.add(port, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        serverSettings.add(SSL, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        serverSettings.add(username, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        serverSettings.add(password, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        serverSettings.add(timeout, gridBagConstraints);

        gridBagConstraintsCommon.gridx = 1;
        gridBagConstraintsCommon.gridy = 0;

        commonPanel.add(serverSettings, gridBagConstraintsCommon);

        return commonPanel;
    }

}

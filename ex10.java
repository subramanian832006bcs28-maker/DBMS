<pre>package STU;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UserManagementGUI extends JFrame {

    static final String URL  = &quot;jdbc:oracle:thin:@localhost:1521:xe&quot;;
    static final String USER = &quot;system&quot;;
    static final String PASS = &quot;np835835&quot;;

    JTextField id, uname, pwd, fname, lname, role;
    JButton insert, update, delete, view, search, clear;
    Connection con;

    boolean isUpdateMode = false;
    JLabel statusLabel;

    public UserManagementGUI() {

        setTitle(&quot;User Management System&quot;);
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            Class.forName(&quot;oracle.jdbc.driver.OracleDriver&quot;);
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;DB Connection Failed&quot;);
        }

        createUI();
    }
    void createUI() {

        Container c = getContentPane();
        c.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder(&quot;User Details&quot;));

        form.add(new JLabel(&quot;User ID&quot;));
        id = new JTextField();
        form.add(id);

        form.add(new JLabel(&quot;Username&quot;));
        uname = new JTextField();
        form.add(uname);

        form.add(new JLabel(&quot;Password&quot;));
        pwd = new JTextField();
        form.add(pwd);

        form.add(new JLabel(&quot;First Name&quot;));
        fname = new JTextField();
        form.add(fname);

        form.add(new JLabel(&quot;Last Name&quot;));
        lname = new JTextField();
        form.add(lname);

        form.add(new JLabel(&quot;Role ID&quot;));
        role = new JTextField();
        form.add(role);

        c.add(form, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        insert = new JButton(&quot;INSERT&quot;);
        update = new JButton(&quot;UPDATE&quot;);
        delete = new JButton(&quot;DELETE&quot;);
        view   = new JButton(&quot;VIEW&quot;);
        search = new JButton(&quot;SEARCH&quot;);
        clear  = new JButton(&quot;CLEAR&quot;);

        btnPanel.add(insert);
        btnPanel.add(update);
        btnPanel.add(delete);
        btnPanel.add(view);
        btnPanel.add(search);
        btnPanel.add(clear);

        statusLabel = new JLabel(&quot;Ready&quot;);

        JPanel south = new JPanel(new BorderLayout());
        south.add(btnPanel, BorderLayout.CENTER);
        south.add(statusLabel, BorderLayout.SOUTH);

        c.add(south, BorderLayout.SOUTH);

        insert.addActionListener(e -&gt; insert());
        update.addActionListener(e -&gt; update());
        delete.addActionListener(e -&gt; delete());
        view.addActionListener(e -&gt; viewUsers());
        search.addActionListener(e -&gt; search());
        clear.addActionListener(e -&gt; clearFields());
    }
    boolean isInteger(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, field + &quot; cannot be empty&quot;);
            return false;
        }
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, field + &quot; must be numeric&quot;);
            return false;
        }
    }

    boolean isAlpha(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, field + &quot; cannot be empty&quot;);
            return false;
        }
        if (!value.matches(&quot;[a-zA-Z ]+&quot;)) {
            JOptionPane.showMessageDialog(this, field + &quot; must contain letters only&quot;);
            return false;
        }
        return true;
    }

    void insert() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;
        if (!isAlpha(uname.getText(), &quot;Username&quot;)) return;
        if (pwd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, &quot;Password cannot be empty&quot;);
            return;
        }
        if (!isAlpha(fname.getText(), &quot;First Name&quot;)) return;
        if (!isAlpha(lname.getText(), &quot;Last Name&quot;)) return;
        if (!isInteger(role.getText(), &quot;Role ID&quot;)) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;INSERT INTO user1 VALUES (?,?,?,?,?,?)&quot;
            );

            ps.setInt(1, Integer.parseInt(id.getText()));
            ps.setString(2, uname.getText());
            ps.setString(3, pwd.getText());
            ps.setString(4, fname.getText());
            ps.setString(5, lname.getText());
            ps.setInt(6, Integer.parseInt(role.getText()));

            ps.executeUpdate();
            con.commit();

            JOptionPane.showMessageDialog(this, &quot;Inserted Successfully&quot;);
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, &quot;User_id already exists&quot;);
        }
    }


    void update() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;UPDATE user1 SET USERNAME=?, FIRST_NAME=?, LAST_NAME=?, ROLE_ID=? WHERE USER_ID=?&quot;
            );

            ps.setString(1, uname.getText());
            ps.setString(2, fname.getText());
            ps.setString(3, lname.getText());
            ps.setInt(4, Integer.parseInt(role.getText()));
            ps.setInt(5, Integer.parseInt(id.getText()));

            int rows = ps.executeUpdate();
            con.commit();

            if (rows &gt; 0) {
                JOptionPane.showMessageDialog(this, &quot;Updated Successfully&quot;);
            } else {
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);
            }

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Update Error&quot;);
        }
    }

    void delete() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps =
                con.prepareStatement(&quot;DELETE FROM user1 WHERE USER_ID=?&quot;);

            ps.setInt(1, Integer.parseInt(id.getText()));
            int rows = ps.executeUpdate();
            con.commit();

            if (rows &gt; 0)
                JOptionPane.showMessageDialog(this, &quot;Deleted Successfully&quot;);
            else
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Delete Error&quot;);
        }
    }

        void search() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps =
                con.prepareStatement(&quot;SELECT * FROM user1 WHERE USER_ID=?&quot;);

            ps.setInt(1, Integer.parseInt(id.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                uname.setText(rs.getString(&quot;USERNAME&quot;));
                pwd.setText(rs.getString(&quot;PASSWORD_HASH&quot;));
                fname.setText(rs.getString(&quot;FIRST_NAME&quot;));
                lname.setText(rs.getString(&quot;LAST_NAME&quot;));
                role.setText(rs.getString(&quot;ROLE_ID&quot;));

                statusLabel.setText(&quot;User Found&quot;);

            } else {
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);
                clearFields();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Search Error&quot;);
        }
    }

      void viewUsers() {

        try {
            ResultSet rs = con.createStatement()
                .executeQuery(&quot;SELECT USER_ID, USERNAME, FIRST_NAME, LAST_NAME, ROLE_ID FROM user1&quot;);

            StringBuilder sb = new StringBuilder(&quot;ID  USERNAME  FIRST  LAST  ROLE\n&quot;);

            while (rs.next()) {
                sb.append(rs.getInt(1)).append(&quot;  &quot;)
                  .append(rs.getString(2)).append(&quot;  &quot;)
                  .append(rs.getString(3)).append(&quot;  &quot;)
                  .append(rs.getString(4)).append(&quot;  &quot;)
                  .append(rs.getInt(5)).append(&quot;\n&quot;);
            }

            JTextArea ta = new JTextArea(sb.toString(), 12, 35);
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;View Error&quot;);
        }
    }

    void clearFields() {
        id.setText(&quot;&quot;);
        uname.setText(&quot;&quot;);
        pwd.setText(&quot;&quot;);
        fname.setText(&quot;&quot;);
        lname.setText(&quot;&quot;);
        role.setText(&quot;&quot;);
        statusLabel.setText(&quot;Ready&quot;);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -&gt;
            new UserManagementGUI().setVisible(true));
    }
}
HACKERANK
package STU;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class hacrr extends JFrame {

    static Connection con;

    // Logged-in state
    static int loggedInCustomerId = -1;
    static String loggedInName = &quot;&quot;;
    static int currentCartId = -1;

    CardLayout mainCards;
    JPanel mainPanel;

    public hacrr() {
        super(&quot;Online Shopping Application&quot;);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        connectDB();

        mainCards = new CardLayout();
        mainPanel = new JPanel(mainCards);
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(buildLoginPanel(),        &quot;LOGIN&quot;);
        mainPanel.add(buildRegisterPanel(),     &quot;REGISTER&quot;);
        mainPanel.add(buildCustomerHome(),      &quot;CUSTOMER_HOME&quot;);
        mainPanel.add(buildAdminPanel(),        &quot;ADMIN&quot;);

        add(mainPanel, BorderLayout.CENTER);
        mainCards.show(mainPanel, &quot;LOGIN&quot;);
        setVisible(true);
    }

    JTextField field(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1),
            new EmptyBorder(4, 6, 4, 6)));
        return f;
    }

    JPasswordField passField(int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1),
            new EmptyBorder(4, 6, 4, 6)));
        return f;
    }

    JButton btn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 12));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBorder(new EmptyBorder(7, 18, 7, 18));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    JButton outlineBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 12));
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setBorder(new LineBorder(Color.BLACK, 1));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 18));
        l.setForeground(Color.BLACK);
        return l;
    }

    JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        l.setForeground(Color.DARK_GRAY);
        return l;
    }

    JLabel smallLbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 11));
        l.setForeground(Color.GRAY);
        return l;
    }

    DefaultTableModel tableModel(String... cols) {
        return new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    JTable makeTable(DefaultTableModel m) {
        JTable t = new JTable(m);
        t.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 12));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(230, 230, 230));
        t.setGridColor(new Color(210, 210, 210));
        t.setSelectionBackground(new Color(180, 180, 180));
        t.setSelectionForeground(Color.BLACK);
        return t;
    }

    JScrollPane scroll(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        return sp;
    }

    JPanel whitePanel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(Color.WHITE);
        return p;
    }

    JPanel grayPanel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(new Color(245, 245, 245));
        return p;
    }

    void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    void showErr(String msg) {
        JOptionPane.showMessageDialog(this, msg, &quot;Error&quot;, JOptionPane.ERROR_MESSAGE);
    }

    boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, &quot;Confirm&quot;,
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String lower = email.toLowerCase();
        if (!lower.endsWith(&quot;@gmail.com&quot;) &amp;&amp; !lower.endsWith(&quot;@yahoo.com&quot;)) return false;
        int atIdx = lower.indexOf(&apos;@&apos;);
        if (atIdx &lt;= 0) return false;
        if (email.contains(&quot; &quot;)) return false;
        return true;
    }

    String validatePassword(String pass) {
        if (pass == null || pass.length() &lt; 8)
            return &quot;Password must be at least 8 characters long.&quot;;
        boolean hasDigit   = pass.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = pass.chars().anyMatch(c -&gt; &quot;!@#$%^&amp;*&quot;.indexOf(c) &gt;= 0);
        if (!hasDigit)
            return &quot;Password must contain at least one number (0-9).&quot;;
        if (!hasSpecial)
            return &quot;Password must contain at least one special character (!@#$%^&amp;*).&quot;;
        return null;
    }

     JPanel buildLoginPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel top = grayPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        top.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        top.add(heading(&quot;Online Shopping Application&quot;));
        root.add(top, BorderLayout.NORTH);

        JPanel center = whitePanel(new GridBagLayout());
        JPanel box = whitePanel(new GridBagLayout());
        box.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(30, 40, 30, 40)));
        box.setPreferredSize(new Dimension(360, 280));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 6, 7, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel(&quot;Login&quot;);
        title.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 16));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; box.add(title, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; g.weightx = 0; box.add(lbl(&quot;Email:&quot;), g);
        JTextField fEmail = field(18);
        g.gridx = 1; g.weightx = 1; box.add(fEmail, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0; box.add(lbl(&quot;Password:&quot;), g);
        JPasswordField fPass = passField(18);
        g.gridx = 1; g.weightx = 1; box.add(fPass, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0; box.add(lbl(&quot;Login as:&quot;), g);
        JComboBox&lt;String&gt; roleBox = new JComboBox&lt;&gt;(new String[]{&quot;Customer&quot;, &quot;Admin&quot;});
        g.gridx = 1; g.weightx = 1; box.add(roleBox, g);

        JButton loginBtn = btn(&quot;Login&quot;);
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        box.add(loginBtn, g);

        JButton regLink = new JButton(&quot;New customer? Register here&quot;);
        regLink.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 11));
        regLink.setBorderPainted(false); regLink.setContentAreaFilled(false);
        regLink.setForeground(Color.DARK_GRAY);
        regLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 5;
        box.add(regLink, g);

        center.add(box);
        root.add(center, BorderLayout.CENTER);

        loginBtn.addActionListener(e -&gt; {
            String email = fEmail.getText().trim();
            String pass  = new String(fPass.getPassword()).trim();
            String role  = (String) roleBox.getSelectedItem();
            if (email.isEmpty() || pass.isEmpty()) { showErr(&quot;Email and password required.&quot;); return; }
            if (&quot;Admin&quot;.equals(role)) {
                if (email.equals(&quot;admin&quot;) &amp;&amp; pass.equals(&quot;admin123&quot;)) {
                    mainCards.show(mainPanel, &quot;ADMIN&quot;);
                } else {
                    showErr(&quot;Invalid admin credentials.&quot;);
                }
            } else {
                if (!isValidEmail(email)) {
                    showErr(&quot;Email must be a valid @gmail.com or @yahoo.com address.&quot;); return;
                }
                String passErr = validatePassword(pass);
                if (passErr != null) { showErr(passErr); return; }

                try {
                    PreparedStatement ps = con.prepareStatement(
                        &quot;SELECT customer_id, name FROM customers_4 WHERE email=? AND password=?&quot;);
                    ps.setString(1, email); ps.setString(2, pass);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        loggedInCustomerId = rs.getInt(1);
                        loggedInName = rs.getString(2);
                        initCart();
                        refreshCustomerHome();
                        mainCards.show(mainPanel, &quot;CUSTOMER_HOME&quot;);
                    } else {
                        showErr(&quot;Invalid email or password.&quot;);
                    }
                    rs.close(); ps.close();
                } catch (SQLException ex) { showErr(ex.getMessage()); }
            }
        });

        regLink.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;REGISTER&quot;));
        return root;
    }

    void initCart() {
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT cart_id FROM cart_4 WHERE customer_id=?&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentCartId = rs.getInt(1);
            } else {
                rs.close(); ps.close();
                PreparedStatement ins = con.prepareStatement(
                    &quot;INSERT INTO cart_4(customer_id, created_date) VALUES(?, SYSDATE)&quot;,
                    new String[]{&quot;cart_id&quot;});
                ins.setInt(1, loggedInCustomerId);
                ins.executeUpdate();
                ResultSet keys = ins.getGeneratedKeys();
                if (keys.next()) currentCartId = keys.getInt(1);
                keys.close(); ins.close();
                return;
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    JPanel buildRegisterPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel top = grayPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        top.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        top.add(heading(&quot;Customer Registration&quot;));
        root.add(top, BorderLayout.NORTH);

        JPanel center = whitePanel(new GridBagLayout());
        JPanel box    = whitePanel(new GridBagLayout());
        box.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(24, 36, 24, 36)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        String[] lbls = {
            &quot;Customer ID:&quot;,
            &quot;Customer Name:&quot;,
            &quot;Customer Type:&quot;,
            &quot;Area:&quot;,
            &quot;Town:&quot;,
            &quot;Email (gmail/yahoo):&quot;,
            &quot;Password:&quot;
        };
        JTextField[] flds = new JTextField[7];
        for (int i = 0; i &lt; lbls.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; box.add(lbl(lbls[i]), g);
            flds[i] = (i == 6) ? passField(20) : field(20);
            g.gridx = 1; g.weightx = 1; box.add(flds[i], g);
        }

        JLabel passHint = smallLbl(&quot;Min 8 chars, 1 number, 1 special character (!@#$%^&amp;*)&quot;);
        g.gridx = 1; g.gridy = 7; g.weightx = 1; box.add(passHint, g);

        JButton regBtn  = btn(&quot;Register&quot;);
        JButton backBtn = outlineBtn(&quot;Back to Login&quot;);
        JPanel btnRow   = whitePanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnRow.add(regBtn); btnRow.add(backBtn);
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        box.add(btnRow, g);

        center.add(box);
        root.add(center, BorderLayout.CENTER);

        regBtn.addActionListener(e -&gt; {
            String custIdStr = flds[0].getText().trim();
            String custName  = flds[1].getText().trim();
            String custType  = flds[2].getText().trim();
            String area      = flds[3].getText().trim();
            String town      = flds[4].getText().trim();
            String email     = flds[5].getText().trim();
            String pass      = flds[6].getText().trim();

            if (custIdStr.isEmpty() || custName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                showErr(&quot;Customer ID, Name, Email and Password are required.&quot;); return;
            }

            int custId;
            try { custId = Integer.parseInt(custIdStr); }
            catch (NumberFormatException ex) { showErr(&quot;Customer ID must be a number.&quot;); return; }

            if (!isValidEmail(email)) {
                showErr(&quot;Email must be a valid @gmail.com or @yahoo.com address.&quot;); return;
            }
            if (email.length() &gt; 20)   { showErr(&quot;Email must be 20 characters or fewer.&quot;); return; }
            if (custName.length() &gt; 15) { showErr(&quot;Customer Name must be 15 characters or fewer.&quot;); return; }
            if (custType.length() &gt; 15) { showErr(&quot;Customer Type must be 15 characters or fewer.&quot;); return; }
            if (area.length() &gt; 15)     { showErr(&quot;Area must be 15 characters or fewer.&quot;); return; }
            if (town.length() &gt; 15)     { showErr(&quot;Town must be 15 characters or fewer.&quot;); return; }

            String passErr = validatePassword(pass);
            if (passErr != null) { showErr(passErr); return; }

            try {
                PreparedStatement chkId = con.prepareStatement(
                    &quot;SELECT customer_id FROM customers_4 WHERE customer_id=?&quot;);
                chkId.setInt(1, custId);
                ResultSet rs = chkId.executeQuery();
                if (rs.next()) { showErr(&quot;Customer ID already exists.&quot;); rs.close(); chkId.close(); return; }
                rs.close(); chkId.close();

                PreparedStatement chkEmail = con.prepareStatement(
                    &quot;SELECT customer_id FROM customers_4 WHERE email=?&quot;);
                chkEmail.setString(1, email);
                rs = chkEmail.executeQuery();
                if (rs.next()) { showErr(&quot;An account with this email already exists.&quot;); rs.close(); chkEmail.close(); return; }
                rs.close(); chkEmail.close();

                PreparedStatement ps = con.prepareStatement(
                    &quot;INSERT INTO customers_4(customer_id, name, email, phone, address, password, created_date) &quot; +
                    &quot;VALUES(?, ?, ?, ?, ?, ?, SYSDATE)&quot;);
                ps.setInt(1, custId);
                ps.setString(2, custName);
                ps.setString(3, email);
                ps.setString(4, custType.isEmpty() ? null : custType);
                String addressVal = &quot;&quot;;
                if (!area.isEmpty() &amp;&amp; !town.isEmpty()) addressVal = area + &quot;, &quot; + town;
                else if (!area.isEmpty()) addressVal = area;
                else if (!town.isEmpty()) addressVal = town;
                ps.setString(5, addressVal.isEmpty() ? null : addressVal);
                ps.setString(6, pass);
                ps.executeUpdate(); ps.close();

                showMsg(&quot;Registration successful. You can now log in.&quot;);
                for (JTextField f : flds) f.setText(&quot;&quot;);
                mainCards.show(mainPanel, &quot;LOGIN&quot;);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        backBtn.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;LOGIN&quot;));
        return root;
    }include &lt;vector&gt;
[24bcs049@mepcolinux ex10]$cat ex10b.prn
Jpanel customerHomeRoot;
JLabel welcomeLbl;
    JTabbedPane customerTabs;

    DefaultTableModel productModel;
    DefaultTableModel cartModel;
    DefaultTableModel orderHistoryModel;
    JTextField searchField;

    JPanel buildCustomerHome() {
        customerHomeRoot = whitePanel(new BorderLayout());

        JPanel topBar = grayPanel(new BorderLayout());
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        JPanel topLeft = grayPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topLeft.add(heading(&quot;Online Shopping&quot;));
        welcomeLbl = smallLbl(&quot;Welcome&quot;);
        topLeft.add(welcomeLbl);
        topBar.add(topLeft, BorderLayout.WEST);
        JPanel topRight = grayPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        JButton logoutBtn = outlineBtn(&quot;Logout&quot;);
        topRight.add(logoutBtn);
        topBar.add(topRight, BorderLayout.EAST);
        customerHomeRoot.add(topBar, BorderLayout.NORTH);

        customerTabs = new JTabbedPane();
        customerTabs.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        customerTabs.setBackground(Color.WHITE);

        customerTabs.addTab(&quot;Products&quot;,      buildProductBrowseTab());
        customerTabs.addTab(&quot;My Cart&quot;,       buildCartTab());
        customerTabs.addTab(&quot;Order History&quot;, buildOrderHistoryTab());
        customerTabs.addTab(&quot;My Profile&quot;,    buildProfileTab());

        customerHomeRoot.add(customerTabs, BorderLayout.CENTER);

        logoutBtn.addActionListener(e -&gt; {
            loggedInCustomerId = -1;
            loggedInName = &quot;&quot;;
            currentCartId = -1;
            mainCards.show(mainPanel, &quot;LOGIN&quot;);
        });

        return customerHomeRoot;
    }

    void refreshCustomerHome() {
        welcomeLbl.setText(&quot;  Logged in as: &quot; + loggedInName);
        loadProducts(null);
        loadCart();
        loadOrderHistory();
        loadProfile();
    }
    JPanel buildProductBrowseTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(lbl(&quot;Search:&quot;));
        searchField = field(18);
        top.add(searchField);
        JButton searchBtn = btn(&quot;Search&quot;);
        JButton clearBtn  = outlineBtn(&quot;Show All&quot;);
        top.add(searchBtn); top.add(clearBtn);
        root.add(top, BorderLayout.NORTH);

        productModel = tableModel(&quot;ID&quot;, &quot;Product Name&quot;, &quot;Category&quot;, &quot;Price (Rs)&quot;, &quot;Stock&quot;, &quot;Description&quot;);
        JTable t = makeTable(productModel);
        root.add(scroll(t), BorderLayout.CENTER);

        JPanel bottom = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        bottom.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        JTextField qtyField = field(4);
        qtyField.setText(&quot;1&quot;);
        bottom.add(lbl(&quot;Qty:&quot;));
        bottom.add(qtyField);
        JButton addCartBtn = btn(&quot;Add to Cart&quot;);
        bottom.add(addCartBtn);
        root.add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -&gt; loadProducts(searchField.getText().trim()));
        clearBtn.addActionListener(e -&gt; { searchField.setText(&quot;&quot;); loadProducts(null); });

        // ── NO stock check here — cart is just a wishlist until checkout ──
        addCartBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a product first.&quot;); return; }
            int qty;
            try { qty = Integer.parseInt(qtyField.getText().trim()); }
            catch (NumberFormatException ex) { showErr(&quot;Enter a valid quantity.&quot;); return; }
            if (qty &lt; 1) { showErr(&quot;Quantity must be at least 1.&quot;); return; }

            int productId = (int) productModel.getValueAt(row, 0);
            addToCart(productId, qty);
        });

        return root;
    }

    void loadProducts(String search) {
        productModel.setRowCount(0);
        try {
            String sql = &quot;SELECT product_id, product_name, category, price, stock_quantity, description FROM products_4&quot;;
            if (search != null &amp;&amp; !search.isEmpty())
                sql += &quot; WHERE LOWER(product_name) LIKE LOWER(?) OR LOWER(category) LIKE LOWER(?)&quot;;
            sql += &quot; ORDER BY product_id&quot;;
            PreparedStatement ps = con.prepareStatement(sql);
            if (search != null &amp;&amp; !search.isEmpty()) {
                ps.setString(1, &quot;%&quot; + search + &quot;%&quot;);
                ps.setString(2, &quot;%&quot; + search + &quot;%&quot;);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productModel.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getDouble(4), rs.getInt(5), rs.getString(6)});
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    void addToCart(int productId, int qty) {
        try {
            PreparedStatement chk = con.prepareStatement(
                &quot;SELECT cart_item_id, quantity FROM cart_items_4 WHERE cart_id=? AND product_id=?&quot;);
            chk.setInt(1, currentCartId); chk.setInt(2, productId);
            ResultSet rs = chk.executeQuery();
            if (rs.next()) {
                int newQty = rs.getInt(2) + qty;
                int itemId = rs.getInt(1);
                rs.close(); chk.close();
                PreparedStatement upd = con.prepareStatement(
                    &quot;UPDATE cart_items_4 SET quantity=? WHERE cart_item_id=?&quot;);
                upd.setInt(1, newQty); upd.setInt(2, itemId);
                upd.executeUpdate(); upd.close();
            } else {
                rs.close(); chk.close();
                PreparedStatement ins = con.prepareStatement(
                    &quot;INSERT INTO cart_items_4(cart_id, product_id, quantity) VALUES(?,?,?)&quot;);
                ins.setInt(1, currentCartId); ins.setInt(2, productId); ins.setInt(3, qty);
                ins.executeUpdate(); ins.close();
            }
            showMsg(&quot;Product added to cart.&quot;);
            loadCart();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    DefaultTableModel cartItemsModel;

    JPanel buildCartTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel title = heading(&quot;Shopping Cart&quot;);
        JPanel titlePanel = whitePanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(title);
        root.add(titlePanel, BorderLayout.NORTH);

        cartItemsModel = tableModel(&quot;Cart Item ID&quot;, &quot;Product&quot;, &quot;Price (Rs)&quot;, &quot;Qty&quot;, &quot;Subtotal&quot;);
        JTable t = makeTable(cartItemsModel);
        root.add(scroll(t), BorderLayout.CENTER);

        JPanel bottom = whitePanel(new BorderLayout());
        bottom.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel actions = whitePanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField qtyEdit = field(4);
        JButton updateBtn   = outlineBtn(&quot;Update Qty&quot;);
        JButton removeBtn   = outlineBtn(&quot;Remove Item&quot;);
        JButton checkoutBtn = btn(&quot;Proceed to Checkout&quot;);
        actions.add(lbl(&quot;New Qty:&quot;)); actions.add(qtyEdit);
        actions.add(updateBtn); actions.add(removeBtn);
        actions.add(checkoutBtn);
        bottom.add(actions, BorderLayout.WEST);

        cartModel = tableModel();
        root.add(bottom, BorderLayout.SOUTH);

        // ── NO stock check here — allow any quantity, stock checked only at checkout ──
        updateBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a cart item.&quot;); return; }
            int itemId = (int) cartItemsModel.getValueAt(row, 0);
            int qty;
            try { qty = Integer.parseInt(qtyEdit.getText().trim()); }
            catch (NumberFormatException ex) { showErr(&quot;Enter valid quantity.&quot;); return; }
            if (qty &lt; 1) { showErr(&quot;Quantity must be at least 1.&quot;); return; }

            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE cart_items_4 SET quantity=? WHERE cart_item_id=?&quot;);
                ps.setInt(1, qty); ps.setInt(2, itemId);
                ps.executeUpdate(); ps.close();
                loadCart();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        removeBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a cart item.&quot;); return; }
            int itemId = (int) cartItemsModel.getValueAt(row, 0);
            if (!confirm(&quot;Remove this item from cart?&quot;)) return;
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;DELETE FROM cart_items_4 WHERE cart_item_id=?&quot;);
                ps.setInt(1, itemId); ps.executeUpdate(); ps.close();
                loadCart();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        checkoutBtn.addActionListener(e -&gt; doCheckout());

        return root;
    }

    void loadCart() {
        if (cartItemsModel == null) return;
        cartItemsModel.setRowCount(0);
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT ci.cart_item_id, p.product_name, p.price, ci.quantity, (p.price*ci.quantity) &quot; +
                &quot;FROM cart_items_4 ci JOIN products_4 p ON ci.product_id=p.product_id &quot; +
                &quot;WHERE ci.cart_id=? ORDER BY ci.cart_item_id&quot;);
            ps.setInt(1, currentCartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItemsModel.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getDouble(3),
                    rs.getInt(4), rs.getDouble(5)});
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    // ── Checkout: stock is validated HERE, right before placing the order ──
    void doCheckout() {
        if (cartItemsModel.getRowCount() == 0) { showErr(&quot;Your cart is empty.&quot;); return; }

              try {
            PreparedStatement stockChk = con.prepareStatement(
                &quot;SELECT p.product_name, p.stock_quantity, ci.quantity &quot; +
                &quot;FROM cart_items_4 ci JOIN products_4 p ON ci.product_id = p.product_id &quot; +
                &quot;WHERE ci.cart_id = ?&quot;);
            stockChk.setInt(1, currentCartId);
            ResultSet rs = stockChk.executeQuery();
            StringBuilder insufficientItems = new StringBuilder();
            while (rs.next()) {
                String productName  = rs.getString(1);
                int    stockAvail   = rs.getInt(2);
                int    cartQty      = rs.getInt(3);
                if (cartQty &gt; stockAvail) {
                    insufficientItems.append(String.format(
                        &quot;• %s — Requested: %d, Available: %d%n&quot;,
                        productName, cartQty, stockAvail));
                }
            }
            rs.close(); stockChk.close();

            if (insufficientItems.length() &gt; 0) {
                showErr(&quot;Cannot place order. Insufficient stock for:\n\n&quot; + insufficientItems +
                        &quot;\nPlease update your cart quantities and try again.&quot;);
                return;
            }
        } catch (SQLException ex) { showErr(ex.getMessage()); return; }

        double total = 0;
        for (int i = 0; i &lt; cartItemsModel.getRowCount(); i++)
            total += (double) cartItemsModel.getValueAt(i, 4);

        String confirmMsg = String.format(&quot;Order Total: Rs %.2f\n\nConfirm purchase?&quot;, total);
        if (!confirm(confirmMsg)) return;

        try {
            PreparedStatement ords = con.prepareStatement(
                &quot;INSERT INTO orders_4(customer_id, order_date, total_amount, order_status) VALUES(?,SYSDATE,?,&apos;Pending&apos;)&quot;,
                new String[]{&quot;order_id&quot;});
            ords.setInt(1, loggedInCustomerId);
            ords.setDouble(2, total);
            ords.executeUpdate();
            ResultSet keys = ords.getGeneratedKeys();
            int orderId = -1;
            if (keys.next()) orderId = keys.getInt(1);
            keys.close(); ords.close();

            PreparedStatement itemPs = con.prepareStatement(
                &quot;SELECT ci.product_id, p.price, ci.quantity FROM cart_items_4 ci &quot; +
                &quot;JOIN products_4 p ON ci.product_id=p.product_id WHERE ci.cart_id=?&quot;);
            itemPs.setInt(1, currentCartId);
            ResultSet itemRs = itemPs.executeQuery();

            PreparedStatement insItem = con.prepareStatement(
                &quot;INSERT INTO order_items_4(order_id, product_id, quantity, price) VALUES(?,?,?,?)&quot;);
            PreparedStatement updStock = con.prepareStatement(
                &quot;UPDATE products_4 SET stock_quantity = stock_quantity - ? WHERE product_id=?&quot;);

            while (itemRs.next()) {
                int pid = itemRs.getInt(1);
                double price = itemRs.getDouble(2);
                int qty = itemRs.getInt(3);
                insItem.setInt(1, orderId); insItem.setInt(2, pid);
                insItem.setInt(3, qty); insItem.setDouble(4, price);
                insItem.executeUpdate();
                updStock.setInt(1, qty); updStock.setInt(2, pid);
                updStock.executeUpdate();
            }
            itemRs.close(); itemPs.close(); insItem.close(); updStock.close();

            PreparedStatement clr = con.prepareStatement(&quot;DELETE FROM cart_items_4 WHERE cart_id=?&quot;);
            clr.setInt(1, currentCartId); clr.executeUpdate(); clr.close();

            showMsg(&quot;Order placed successfully. Order ID: &quot; + orderId);
            loadCart();
            loadOrderHistory();
            loadProducts(null);
            customerTabs.setSelectedIndex(2);
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    JPanel buildOrderHistoryTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        orderHistoryModel = tableModel(&quot;Order ID&quot;, &quot;Date&quot;, &quot;Total (Rs)&quot;, &quot;Status&quot;);
        JTable masterTable = makeTable(orderHistoryModel);

        DefaultTableModel itemDetailModel = tableModel(&quot;Product&quot;, &quot;Qty&quot;, &quot;Price&quot;, &quot;Subtotal&quot;);
        JTable detailTable = makeTable(itemDetailModel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll(masterTable), scroll(detailTable));
        split.setDividerLocation(280);
        split.setResizeWeight(0.6);
        root.add(split, BorderLayout.CENTER);

        JPanel topRow = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        topRow.add(heading(&quot;Order History&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        topRow.add(refreshBtn);
        root.add(topRow, BorderLayout.NORTH);

        JLabel detailLbl = lbl(&quot;Select an order above to see its items.&quot;);
        root.add(detailLbl, BorderLayout.SOUTH);

        masterTable.getSelectionModel().addListSelectionListener(e -&gt; {
            int row = masterTable.getSelectedRow();
            if (row &lt; 0) return;
            int orderId = (int) orderHistoryModel.getValueAt(row, 0);
            itemDetailModel.setRowCount(0);
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;SELECT p.product_name, oi.quantity, oi.price, (oi.quantity*oi.price) &quot; +
                    &quot;FROM order_items_4 oi JOIN products_4 p ON oi.product_id=p.product_id &quot; +
                    &quot;WHERE oi.order_id=?&quot;);
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    itemDetailModel.addRow(new Object[]{rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)});
                rs.close(); ps.close();
                detailLbl.setText(&quot;Items for Order #&quot; + orderId);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        refreshBtn.addActionListener(e -&gt; loadOrderHistory());
        return root;
    }

    void loadOrderHistory() {
        if (orderHistoryModel == null) return;
        orderHistoryModel.setRowCount(0);
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT order_id, order_date, total_amount, order_status FROM orders_4 &quot; +
                &quot;WHERE customer_id=? ORDER BY order_id DESC&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                orderHistoryModel.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4)});
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    JTextField pfName, pfPhone, pfAddr;

    JPanel buildProfileTab() {
        JPanel root = whitePanel(new BorderLayout());
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel form = whitePanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(24, 32, 24, 32)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = heading(&quot;My Profile&quot;);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; form.add(title, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; g.weightx = 0; form.add(lbl(&quot;Name:&quot;), g);
        pfName = field(20); g.gridx = 1; g.weightx = 1; form.add(pfName, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0; form.add(lbl(&quot;Phone:&quot;), g);
        pfPhone = field(20); g.gridx = 1; g.weightx = 1; form.add(pfPhone, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0; form.add(lbl(&quot;Address:&quot;), g);
        pfAddr = field(20); g.gridx = 1; g.weightx = 1; form.add(pfAddr, g);

        JButton saveBtn = btn(&quot;Save Changes&quot;);
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.WEST;
        form.add(saveBtn, g);

        JPanel wrap = whitePanel(new FlowLayout(FlowLayout.LEFT));
        wrap.add(form);
        root.add(wrap, BorderLayout.NORTH);

        saveBtn.addActionListener(e -&gt; {
            String name  = pfName.getText().trim();
            String phone = pfPhone.getText().trim();
            String addr  = pfAddr.getText().trim();
            if (name.isEmpty()) { showErr(&quot;Name cannot be empty.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE customers_4 SET name=?, phone=?, address=? WHERE customer_id=?&quot;);
                ps.setString(1, name);
                ps.setString(2, phone.isEmpty() ? null : phone);
                ps.setString(3, addr.isEmpty() ? null : addr);
                ps.setInt(4, loggedInCustomerId);
                ps.executeUpdate(); ps.close();
                loggedInName = name;
                welcomeLbl.setText(&quot;  Logged in as: &quot; + loggedInName);
                showMsg(&quot;Profile updated.&quot;);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        return root;
    }

    void loadProfile() {
        if (pfName == null) return;
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT name, phone, address FROM customers_4 WHERE customer_id=?&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pfName.setText(rs.getString(1) == null ? &quot;&quot; : rs.getString(1));
                pfPhone.setText(rs.getString(2) == null ? &quot;&quot; : rs.getString(2));
                pfAddr.setText(rs.getString(3) == null ? &quot;&quot; : rs.getString(3));
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
        JPanel buildAdminPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel topBar = grayPanel(new BorderLayout());
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        JPanel topLeft = grayPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topLeft.add(heading(&quot;Admin Dashboard&quot;));
        topBar.add(topLeft, BorderLayout.WEST);
        JPanel topRight = grayPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        JButton logoutBtn = outlineBtn(&quot;Logout&quot;);
        topRight.add(logoutBtn);
        topBar.add(topRight, BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        tabs.addTab(&quot;Products&quot;,  buildAdminProductsTab());
        tabs.addTab(&quot;Orders&quot;,    buildAdminOrdersTab());
        tabs.addTab(&quot;Customers&quot;, buildAdminCustomersTab());
        root.add(tabs, BorderLayout.CENTER);

        logoutBtn.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;LOGIN&quot;));
        return root;
    }
    DefaultTableModel adminProdModel;

    JPanel buildAdminProductsTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        String[] lbls   = {&quot;Product ID&quot;, &quot;Product Name&quot;, &quot;Category&quot;, &quot;Price (Rs)&quot;, &quot;Stock Qty&quot;, &quot;Description&quot;};
        JTextField[] flds = new JTextField[6];
        JPanel form = whitePanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(16, 16, 16, 16)));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 6, 5, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i &lt; lbls.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; form.add(lbl(lbls[i]), g);
            flds[i] = field(16); g.gridx = 1; g.weightx = 1; form.add(flds[i], g);
        }

        JButton viewBtn   = btn(&quot;View All&quot;);
        JButton insertBtn = outlineBtn(&quot;Insert&quot;);
        JButton updateBtn = outlineBtn(&quot;Update&quot;);
        JButton deleteBtn = outlineBtn(&quot;Delete&quot;);
        JButton clearBtn  = outlineBtn(&quot;Clear&quot;);

        JPanel btnRow = whitePanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnRow.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        btnRow.add(viewBtn); btnRow.add(insertBtn); btnRow.add(updateBtn);
        btnRow.add(deleteBtn); btnRow.add(clearBtn);

        JPanel left = whitePanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER);
        left.add(btnRow, BorderLayout.SOUTH);
        left.setPreferredSize(new Dimension(290, 0));

        adminProdModel = tableModel(&quot;ID&quot;, &quot;Name&quot;, &quot;Category&quot;, &quot;Price&quot;, &quot;Stock&quot;, &quot;Description&quot;);
        JTable t = makeTable(adminProdModel);
        t.getSelectionModel().addListSelectionListener(e -&gt; {
            int row = t.getSelectedRow(); if (row &lt; 0) return;
            for (int i = 0; i &lt; flds.length; i++)
                flds[i].setText(adminProdModel.getValueAt(row, i) == null ? &quot;&quot; : adminProdModel.getValueAt(row, i).toString());
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, scroll(t));
        split.setDividerLocation(290);
        split.setDividerSize(1);
        root.add(split, BorderLayout.CENTER);

        viewBtn.addActionListener(e -&gt; loadAdminProducts());

        insertBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank() || flds[1].getText().isBlank()) { showErr(&quot;ID and Name required.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;INSERT INTO products_4(product_id,product_name,category,price,stock_quantity,description) VALUES(?,?,?,?,?,?)&quot;);
                ps.setInt(1, Integer.parseInt(flds[0].getText().trim()));
                ps.setString(2, flds[1].getText().trim());
                ps.setString(3, flds[2].getText().isEmpty() ? null : flds[2].getText().trim());
                ps.setDouble(4, flds[3].getText().isEmpty() ? 0 : Double.parseDouble(flds[3].getText().trim()));
                ps.setInt(5, flds[4].getText().isEmpty() ? 0 : Integer.parseInt(flds[4].getText().trim()));
                ps.setString(6, flds[5].getText().isEmpty() ? null : flds[5].getText().trim());
                ps.executeUpdate(); ps.close();
                showMsg(&quot;Product inserted.&quot;); loadAdminProducts();
                for (JTextField f : flds) f.setText(&quot;&quot;);
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        updateBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank()) { showErr(&quot;Select a product first.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE products_4 SET product_name=?,category=?,price=?,stock_quantity=?,description=? WHERE product_id=?&quot;);
                ps.setString(1, flds[1].getText().trim());
                ps.setString(2, flds[2].getText().isEmpty() ? null : flds[2].getText().trim());
                ps.setDouble(3, flds[3].getText().isEmpty() ? 0 : Double.parseDouble(flds[3].getText().trim()));
                ps.setInt(4, flds[4].getText().isEmpty() ? 0 : Integer.parseInt(flds[4].getText().trim()));
                ps.setString(5, flds[5].getText().isEmpty() ? null : flds[5].getText().trim());
                ps.setInt(6, Integer.parseInt(flds[0].getText().trim()));
                if (ps.executeUpdate() &gt; 0) { showMsg(&quot;Product updated.&quot;); loadAdminProducts(); } else showErr(&quot;ID not found.&quot;);
                ps.close();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        deleteBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank()) { showErr(&quot;Select a product.&quot;); return; }
            if (!confirm(&quot;Delete product #&quot; + flds[0].getText() + &quot;?&quot;)) return;
            try {
                PreparedStatement ps = con.prepareStatement(&quot;DELETE FROM products_4 WHERE product_id=?&quot;);
                ps.setInt(1, Integer.parseInt(flds[0].getText().trim()));
                if (ps.executeUpdate() &gt; 0) { showMsg(&quot;Deleted.&quot;); loadAdminProducts(); for (JTextField f : flds) f.setText(&quot;&quot;); }
                else showErr(&quot;Not found.&quot;);
                ps.close();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        clearBtn.addActionListener(e -&gt; { for (JTextField f : flds) f.setText(&quot;&quot;); });

        SwingUtilities.invokeLater(this::loadAdminProducts);
        return root;
    }

    void loadAdminProducts() {
        if (adminProdModel == null) return;
        adminProdModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT product_id,product_name,category,price,stock_quantity,description FROM products_4 ORDER BY product_id&quot;);
            while (rs.next())
                adminProdModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getDouble(4),rs.getInt(5),rs.getString(6)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    DefaultTableModel adminOrderModel;

    JPanel buildAdminOrdersTab() {
        JPanel root = whitePanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(heading(&quot;Order Management&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        top.add(refreshBtn);
        root.add(top, BorderLayout.NORTH);

        adminOrderModel = tableModel(&quot;Order ID&quot;, &quot;Customer&quot;, &quot;Date&quot;, &quot;Total (Rs)&quot;, &quot;Status&quot;);
        JTable t = makeTable(adminOrderModel);

        JComboBox&lt;String&gt; statusBox = new JComboBox&lt;&gt;(new String[]{&quot;Pending&quot;, &quot;Shipped&quot;, &quot;Delivered&quot;});
        JButton updateBtn = btn(&quot;Update Status&quot;);
        JPanel bot = whitePanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        bot.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        bot.add(lbl(&quot;Set Status:&quot;));
        bot.add(statusBox);
        bot.add(updateBtn);

        root.add(scroll(t), BorderLayout.CENTER);
        root.add(bot, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -&gt; loadAdminOrders());
        updateBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select an order.&quot;); return; }
            int orderId = (int) adminOrderModel.getValueAt(row, 0);
            String status = (String) statusBox.getSelectedItem();
            try {
                PreparedStatement ps = con.prepareStatement(&quot;UPDATE orders_4 SET order_status=? WHERE order_id=?&quot;);
                ps.setString(1, status); ps.setInt(2, orderId);
                ps.executeUpdate(); ps.close();
                showMsg(&quot;Order #&quot; + orderId + &quot; status updated to &quot; + status);
                loadAdminOrders();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        SwingUtilities.invokeLater(this::loadAdminOrders);
        return root;
    }

    void loadAdminOrders() {
        if (adminOrderModel == null) return;
        adminOrderModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT o.order_id, c.name, o.order_date, o.total_amount, o.order_status &quot; +
                &quot;FROM orders_4 o LEFT JOIN customers_4 c ON o.customer_id=c.customer_id ORDER BY o.order_id DESC&quot;);
            while (rs.next())
                adminOrderModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    DefaultTableModel adminCustModel;

    JPanel buildAdminCustomersTab() {
        JPanel root = whitePanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(heading(&quot;Customer Records&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        top.add(refreshBtn);
        root.add(top, BorderLayout.NORTH);

        adminCustModel = tableModel(&quot;ID&quot;, &quot;Name&quot;, &quot;Email&quot;, &quot;Phone&quot;, &quot;Address&quot;, &quot;Registered&quot;);
        JTable t = makeTable(adminCustModel);
        root.add(scroll(t), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -&gt; loadAdminCustomers());
        SwingUtilities.invokeLater(this::loadAdminCustomers);
        return root;
    }

    void loadAdminCustomers() {
        if (adminCustModel == null) return;
        adminCustModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT customer_id,name,email,phone,address,created_date FROM customers_4 ORDER BY customer_id&quot;);
            while (rs.next())
                adminCustModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    static void connectDB() {
        try {
            Class.forName(&quot;oracle.jdbc.driver.OracleDriver&quot;);
            con = DriverManager.getConnection(
                &quot;jdbc:oracle:thin:@localhost:1521:xe&quot;, &quot;system&quot;, &quot;np835835&quot;);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                &quot;DB Connection Failed:\n&quot; + e.getMessage(), &quot;Error&quot;, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hacrr::new);
    }
}

[24bcs049@mepcolinux ex10]$cat ex10.prn
package STU;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UserManagementGUI extends JFrame {

    static final String URL  = &quot;jdbc:oracle:thin:@localhost:1521:xe&quot;;
    static final String USER = &quot;system&quot;;
    static final String PASS = &quot;np835835&quot;;

    JTextField id, uname, pwd, fname, lname, role;
    JButton insert, update, delete, view, search, clear;
    Connection con;

    boolean isUpdateMode = false;
    JLabel statusLabel;

    public UserManagementGUI() {

        setTitle(&quot;User Management System&quot;);
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            Class.forName(&quot;oracle.jdbc.driver.OracleDriver&quot;);
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;DB Connection Failed&quot;);
        }

        createUI();
    }
    void createUI() {

        Container c = getContentPane();
        c.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder(&quot;User Details&quot;));

        form.add(new JLabel(&quot;User ID&quot;));
        id = new JTextField();
        form.add(id);

        form.add(new JLabel(&quot;Username&quot;));
        uname = new JTextField();
        form.add(uname);

        form.add(new JLabel(&quot;Password&quot;));
        pwd = new JTextField();
        form.add(pwd);

        form.add(new JLabel(&quot;First Name&quot;));
        fname = new JTextField();
        form.add(fname);

        form.add(new JLabel(&quot;Last Name&quot;));
        lname = new JTextField();
        form.add(lname);

        form.add(new JLabel(&quot;Role ID&quot;));
        role = new JTextField();
        form.add(role);

        c.add(form, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        insert = new JButton(&quot;INSERT&quot;);
        update = new JButton(&quot;UPDATE&quot;);
        delete = new JButton(&quot;DELETE&quot;);
        view   = new JButton(&quot;VIEW&quot;);
        search = new JButton(&quot;SEARCH&quot;);
        clear  = new JButton(&quot;CLEAR&quot;);

        btnPanel.add(insert);
        btnPanel.add(update);
        btnPanel.add(delete);
        btnPanel.add(view);
        btnPanel.add(search);
        btnPanel.add(clear);

        statusLabel = new JLabel(&quot;Ready&quot;);

        JPanel south = new JPanel(new BorderLayout());
        south.add(btnPanel, BorderLayout.CENTER);
        south.add(statusLabel, BorderLayout.SOUTH);

        c.add(south, BorderLayout.SOUTH);

        insert.addActionListener(e -&gt; insert());
        update.addActionListener(e -&gt; update());
        delete.addActionListener(e -&gt; delete());
        view.addActionListener(e -&gt; viewUsers());
        search.addActionListener(e -&gt; search());
        clear.addActionListener(e -&gt; clearFields());
    }
    boolean isInteger(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, field + &quot; cannot be empty&quot;);
            return false;
        }
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, field + &quot; must be numeric&quot;);
            return false;
        }
    }

    boolean isAlpha(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, field + &quot; cannot be empty&quot;);
            return false;
        }
        if (!value.matches(&quot;[a-zA-Z ]+&quot;)) {
            JOptionPane.showMessageDialog(this, field + &quot; must contain letters only&quot;);
            return false;
        }
        return true;
    }

    void insert() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;
        if (!isAlpha(uname.getText(), &quot;Username&quot;)) return;
        if (pwd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, &quot;Password cannot be empty&quot;);
            return;
        }
        if (!isAlpha(fname.getText(), &quot;First Name&quot;)) return;
        if (!isAlpha(lname.getText(), &quot;Last Name&quot;)) return;
        if (!isInteger(role.getText(), &quot;Role ID&quot;)) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;INSERT INTO user1 VALUES (?,?,?,?,?,?)&quot;
            );

            ps.setInt(1, Integer.parseInt(id.getText()));
            ps.setString(2, uname.getText());
            ps.setString(3, pwd.getText());
            ps.setString(4, fname.getText());
            ps.setString(5, lname.getText());
            ps.setInt(6, Integer.parseInt(role.getText()));

            ps.executeUpdate();
            con.commit();

            JOptionPane.showMessageDialog(this, &quot;Inserted Successfully&quot;);
            clearFields();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, &quot;User_id already exists&quot;);
        }
    }


    void update() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;UPDATE user1 SET USERNAME=?, FIRST_NAME=?, LAST_NAME=?, ROLE_ID=? WHERE USER_ID=?&quot;
            );

            ps.setString(1, uname.getText());
            ps.setString(2, fname.getText());
            ps.setString(3, lname.getText());
            ps.setInt(4, Integer.parseInt(role.getText()));
            ps.setInt(5, Integer.parseInt(id.getText()));

            int rows = ps.executeUpdate();
            con.commit();

            if (rows &gt; 0) {
                JOptionPane.showMessageDialog(this, &quot;Updated Successfully&quot;);
            } else {
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);
            }

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Update Error&quot;);
        }
    }

    void delete() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps =
                con.prepareStatement(&quot;DELETE FROM user1 WHERE USER_ID=?&quot;);

            ps.setInt(1, Integer.parseInt(id.getText()));
            int rows = ps.executeUpdate();
            con.commit();

            if (rows &gt; 0)
                JOptionPane.showMessageDialog(this, &quot;Deleted Successfully&quot;);
            else
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Delete Error&quot;);
        }
    }

        void search() {

        if (!isInteger(id.getText(), &quot;User ID&quot;)) return;

        try {
            PreparedStatement ps =
                con.prepareStatement(&quot;SELECT * FROM user1 WHERE USER_ID=?&quot;);

            ps.setInt(1, Integer.parseInt(id.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                uname.setText(rs.getString(&quot;USERNAME&quot;));
                pwd.setText(rs.getString(&quot;PASSWORD_HASH&quot;));
                fname.setText(rs.getString(&quot;FIRST_NAME&quot;));
                lname.setText(rs.getString(&quot;LAST_NAME&quot;));
                role.setText(rs.getString(&quot;ROLE_ID&quot;));

                statusLabel.setText(&quot;User Found&quot;);

            } else {
                JOptionPane.showMessageDialog(this, &quot;User not found&quot;);
                clearFields();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;Search Error&quot;);
        }
    }

      void viewUsers() {

        try {
            ResultSet rs = con.createStatement()
                .executeQuery(&quot;SELECT USER_ID, USERNAME, FIRST_NAME, LAST_NAME, ROLE_ID FROM user1&quot;);

            StringBuilder sb = new StringBuilder(&quot;ID  USERNAME  FIRST  LAST  ROLE\n&quot;);

            while (rs.next()) {
                sb.append(rs.getInt(1)).append(&quot;  &quot;)
                  .append(rs.getString(2)).append(&quot;  &quot;)
                  .append(rs.getString(3)).append(&quot;  &quot;)
                  .append(rs.getString(4)).append(&quot;  &quot;)
                  .append(rs.getInt(5)).append(&quot;\n&quot;);
            }

            JTextArea ta = new JTextArea(sb.toString(), 12, 35);
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, &quot;View Error&quot;);
        }
    }

    void clearFields() {
        id.setText(&quot;&quot;);
        uname.setText(&quot;&quot;);
        pwd.setText(&quot;&quot;);
        fname.setText(&quot;&quot;);
        lname.setText(&quot;&quot;);
        role.setText(&quot;&quot;);
        statusLabel.setText(&quot;Ready&quot;);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -&gt;
            new UserManagementGUI().setVisible(true));
    }
}
HACKERANK
package STU;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class hacrr extends JFrame {

    static Connection con;

    // Logged-in state
    static int loggedInCustomerId = -1;
    static String loggedInName = &quot;&quot;;
    static int currentCartId = -1;

    CardLayout mainCards;
    JPanel mainPanel;

    public hacrr() {
        super(&quot;Online Shopping Application&quot;);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        connectDB();

        mainCards = new CardLayout();
        mainPanel = new JPanel(mainCards);
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(buildLoginPanel(),        &quot;LOGIN&quot;);
        mainPanel.add(buildRegisterPanel(),     &quot;REGISTER&quot;);
        mainPanel.add(buildCustomerHome(),      &quot;CUSTOMER_HOME&quot;);
        mainPanel.add(buildAdminPanel(),        &quot;ADMIN&quot;);

        add(mainPanel, BorderLayout.CENTER);
        mainCards.show(mainPanel, &quot;LOGIN&quot;);
        setVisible(true);
    }

    JTextField field(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1),
            new EmptyBorder(4, 6, 4, 6)));
        return f;
    }

    JPasswordField passField(int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1),
            new EmptyBorder(4, 6, 4, 6)));
        return f;
    }

    JButton btn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 12));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBorder(new EmptyBorder(7, 18, 7, 18));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    JButton outlineBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 12));
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setBorder(new LineBorder(Color.BLACK, 1));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 18));
        l.setForeground(Color.BLACK);
        return l;
    }

    JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        l.setForeground(Color.DARK_GRAY);
        return l;
    }

    JLabel smallLbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 11));
        l.setForeground(Color.GRAY);
        return l;
    }

    DefaultTableModel tableModel(String... cols) {
        return new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    JTable makeTable(DefaultTableModel m) {
        JTable t = new JTable(m);
        t.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 12));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(230, 230, 230));
        t.setGridColor(new Color(210, 210, 210));
        t.setSelectionBackground(new Color(180, 180, 180));
        t.setSelectionForeground(Color.BLACK);
        return t;
    }

    JScrollPane scroll(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        return sp;
    }

    JPanel whitePanel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(Color.WHITE);
        return p;
    }

    JPanel grayPanel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(new Color(245, 245, 245));
        return p;
    }

    void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    void showErr(String msg) {
        JOptionPane.showMessageDialog(this, msg, &quot;Error&quot;, JOptionPane.ERROR_MESSAGE);
    }

    boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, &quot;Confirm&quot;,
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String lower = email.toLowerCase();
        if (!lower.endsWith(&quot;@gmail.com&quot;) &amp;&amp; !lower.endsWith(&quot;@yahoo.com&quot;)) return false;
        int atIdx = lower.indexOf(&apos;@&apos;);
        if (atIdx &lt;= 0) return false;
        if (email.contains(&quot; &quot;)) return false;
        return true;
    }

    String validatePassword(String pass) {
        if (pass == null || pass.length() &lt; 8)
            return &quot;Password must be at least 8 characters long.&quot;;
        boolean hasDigit   = pass.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = pass.chars().anyMatch(c -&gt; &quot;!@#$%^&amp;*&quot;.indexOf(c) &gt;= 0);
        if (!hasDigit)
            return &quot;Password must contain at least one number (0-9).&quot;;
        if (!hasSpecial)
            return &quot;Password must contain at least one special character (!@#$%^&amp;*).&quot;;
        return null;
    }

     JPanel buildLoginPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel top = grayPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        top.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        top.add(heading(&quot;Online Shopping Application&quot;));
        root.add(top, BorderLayout.NORTH);

        JPanel center = whitePanel(new GridBagLayout());
        JPanel box = whitePanel(new GridBagLayout());
        box.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(30, 40, 30, 40)));
        box.setPreferredSize(new Dimension(360, 280));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 6, 7, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel(&quot;Login&quot;);
        title.setFont(new Font(&quot;SansSerif&quot;, Font.BOLD, 16));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; box.add(title, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; g.weightx = 0; box.add(lbl(&quot;Email:&quot;), g);
        JTextField fEmail = field(18);
        g.gridx = 1; g.weightx = 1; box.add(fEmail, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0; box.add(lbl(&quot;Password:&quot;), g);
        JPasswordField fPass = passField(18);
        g.gridx = 1; g.weightx = 1; box.add(fPass, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0; box.add(lbl(&quot;Login as:&quot;), g);
        JComboBox&lt;String&gt; roleBox = new JComboBox&lt;&gt;(new String[]{&quot;Customer&quot;, &quot;Admin&quot;});
        g.gridx = 1; g.weightx = 1; box.add(roleBox, g);

        JButton loginBtn = btn(&quot;Login&quot;);
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        box.add(loginBtn, g);

        JButton regLink = new JButton(&quot;New customer? Register here&quot;);
        regLink.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 11));
        regLink.setBorderPainted(false); regLink.setContentAreaFilled(false);
        regLink.setForeground(Color.DARK_GRAY);
        regLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 5;
        box.add(regLink, g);

        center.add(box);
        root.add(center, BorderLayout.CENTER);

        loginBtn.addActionListener(e -&gt; {
            String email = fEmail.getText().trim();
            String pass  = new String(fPass.getPassword()).trim();
            String role  = (String) roleBox.getSelectedItem();
            if (email.isEmpty() || pass.isEmpty()) { showErr(&quot;Email and password required.&quot;); return; }
            if (&quot;Admin&quot;.equals(role)) {
                if (email.equals(&quot;admin&quot;) &amp;&amp; pass.equals(&quot;admin123&quot;)) {
                    mainCards.show(mainPanel, &quot;ADMIN&quot;);
                } else {
                    showErr(&quot;Invalid admin credentials.&quot;);
                }
            } else {
                if (!isValidEmail(email)) {
                    showErr(&quot;Email must be a valid @gmail.com or @yahoo.com address.&quot;); return;
                }
                String passErr = validatePassword(pass);
                if (passErr != null) { showErr(passErr); return; }

                try {
                    PreparedStatement ps = con.prepareStatement(
                        &quot;SELECT customer_id, name FROM customers_4 WHERE email=? AND password=?&quot;);
                    ps.setString(1, email); ps.setString(2, pass);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        loggedInCustomerId = rs.getInt(1);
                        loggedInName = rs.getString(2);
                        initCart();
                        refreshCustomerHome();
                        mainCards.show(mainPanel, &quot;CUSTOMER_HOME&quot;);
                    } else {
                        showErr(&quot;Invalid email or password.&quot;);
                    }
                    rs.close(); ps.close();
                } catch (SQLException ex) { showErr(ex.getMessage()); }
            }
        });

        regLink.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;REGISTER&quot;));
        return root;
    }

    void initCart() {
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT cart_id FROM cart_4 WHERE customer_id=?&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentCartId = rs.getInt(1);
            } else {
                rs.close(); ps.close();
                PreparedStatement ins = con.prepareStatement(
                    &quot;INSERT INTO cart_4(customer_id, created_date) VALUES(?, SYSDATE)&quot;,
                    new String[]{&quot;cart_id&quot;});
                ins.setInt(1, loggedInCustomerId);
                ins.executeUpdate();
                ResultSet keys = ins.getGeneratedKeys();
                if (keys.next()) currentCartId = keys.getInt(1);
                keys.close(); ins.close();
                return;
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    JPanel buildRegisterPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel top = grayPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        top.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        top.add(heading(&quot;Customer Registration&quot;));
        root.add(top, BorderLayout.NORTH);

        JPanel center = whitePanel(new GridBagLayout());
        JPanel box    = whitePanel(new GridBagLayout());
        box.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(24, 36, 24, 36)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        String[] lbls = {
            &quot;Customer ID:&quot;,
            &quot;Customer Name:&quot;,
            &quot;Customer Type:&quot;,
            &quot;Area:&quot;,
            &quot;Town:&quot;,
            &quot;Email (gmail/yahoo):&quot;,
            &quot;Password:&quot;
        };
        JTextField[] flds = new JTextField[7];
        for (int i = 0; i &lt; lbls.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; box.add(lbl(lbls[i]), g);
            flds[i] = (i == 6) ? passField(20) : field(20);
            g.gridx = 1; g.weightx = 1; box.add(flds[i], g);
        }

        JLabel passHint = smallLbl(&quot;Min 8 chars, 1 number, 1 special character (!@#$%^&amp;*)&quot;);
        g.gridx = 1; g.gridy = 7; g.weightx = 1; box.add(passHint, g);

        JButton regBtn  = btn(&quot;Register&quot;);
        JButton backBtn = outlineBtn(&quot;Back to Login&quot;);
        JPanel btnRow   = whitePanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnRow.add(regBtn); btnRow.add(backBtn);
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        box.add(btnRow, g);

        center.add(box);
        root.add(center, BorderLayout.CENTER);

        regBtn.addActionListener(e -&gt; {
            String custIdStr = flds[0].getText().trim();
            String custName  = flds[1].getText().trim();
            String custType  = flds[2].getText().trim();
            String area      = flds[3].getText().trim();
            String town      = flds[4].getText().trim();
            String email     = flds[5].getText().trim();
            String pass      = flds[6].getText().trim();

            if (custIdStr.isEmpty() || custName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                showErr(&quot;Customer ID, Name, Email and Password are required.&quot;); return;
            }

            int custId;
            try { custId = Integer.parseInt(custIdStr); }
            catch (NumberFormatException ex) { showErr(&quot;Customer ID must be a number.&quot;); return; }

            if (!isValidEmail(email)) {
                showErr(&quot;Email must be a valid @gmail.com or @yahoo.com address.&quot;); return;
            }
            if (email.length() &gt; 20)   { showErr(&quot;Email must be 20 characters or fewer.&quot;); return; }
            if (custName.length() &gt; 15) { showErr(&quot;Customer Name must be 15 characters or fewer.&quot;); return; }
            if (custType.length() &gt; 15) { showErr(&quot;Customer Type must be 15 characters or fewer.&quot;); return; }
            if (area.length() &gt; 15)     { showErr(&quot;Area must be 15 characters or fewer.&quot;); return; }
            if (town.length() &gt; 15)     { showErr(&quot;Town must be 15 characters or fewer.&quot;); return; }

            String passErr = validatePassword(pass);
            if (passErr != null) { showErr(passErr); return; }

            try {
                PreparedStatement chkId = con.prepareStatement(
                    &quot;SELECT customer_id FROM customers_4 WHERE customer_id=?&quot;);
                chkId.setInt(1, custId);
                ResultSet rs = chkId.executeQuery();
                if (rs.next()) { showErr(&quot;Customer ID already exists.&quot;); rs.close(); chkId.close(); return; }
                rs.close(); chkId.close();

                PreparedStatement chkEmail = con.prepareStatement(
                    &quot;SELECT customer_id FROM customers_4 WHERE email=?&quot;);
                chkEmail.setString(1, email);
                rs = chkEmail.executeQuery();
                if (rs.next()) { showErr(&quot;An account with this email already exists.&quot;); rs.close(); chkEmail.close(); return; }
                rs.close(); chkEmail.close();

                PreparedStatement ps = con.prepareStatement(
                    &quot;INSERT INTO customers_4(customer_id, name, email, phone, address, password, created_date) &quot; +
                    &quot;VALUES(?, ?, ?, ?, ?, ?, SYSDATE)&quot;);
                ps.setInt(1, custId);
                ps.setString(2, custName);
                ps.setString(3, email);
                ps.setString(4, custType.isEmpty() ? null : custType);
                String addressVal = &quot;&quot;;
                if (!area.isEmpty() &amp;&amp; !town.isEmpty()) addressVal = area + &quot;, &quot; + town;
                else if (!area.isEmpty()) addressVal = area;
                else if (!town.isEmpty()) addressVal = town;
                ps.setString(5, addressVal.isEmpty() ? null : addressVal);
                ps.setString(6, pass);
                ps.executeUpdate(); ps.close();

                showMsg(&quot;Registration successful. You can now log in.&quot;);
                for (JTextField f : flds) f.setText(&quot;&quot;);
                mainCards.show(mainPanel, &quot;LOGIN&quot;);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        backBtn.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;LOGIN&quot;));
        return root;
    }
    
    JPanel customerHomeRoot;
    JLabel welcomeLbl;
    JTabbedPane customerTabs;

    DefaultTableModel productModel;
    DefaultTableModel cartModel;
    DefaultTableModel orderHistoryModel;
    JTextField searchField;

    JPanel buildCustomerHome() {
        customerHomeRoot = whitePanel(new BorderLayout());

        JPanel topBar = grayPanel(new BorderLayout());
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        JPanel topLeft = grayPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topLeft.add(heading(&quot;Online Shopping&quot;));
        welcomeLbl = smallLbl(&quot;Welcome&quot;);
        topLeft.add(welcomeLbl);
        topBar.add(topLeft, BorderLayout.WEST);
        JPanel topRight = grayPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        JButton logoutBtn = outlineBtn(&quot;Logout&quot;);
        topRight.add(logoutBtn);
        topBar.add(topRight, BorderLayout.EAST);
        customerHomeRoot.add(topBar, BorderLayout.NORTH);

        customerTabs = new JTabbedPane();
        customerTabs.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        customerTabs.setBackground(Color.WHITE);

        customerTabs.addTab(&quot;Products&quot;,      buildProductBrowseTab());
        customerTabs.addTab(&quot;My Cart&quot;,       buildCartTab());
        customerTabs.addTab(&quot;Order History&quot;, buildOrderHistoryTab());
        customerTabs.addTab(&quot;My Profile&quot;,    buildProfileTab());

        customerHomeRoot.add(customerTabs, BorderLayout.CENTER);

        logoutBtn.addActionListener(e -&gt; {
            loggedInCustomerId = -1;
            loggedInName = &quot;&quot;;
            currentCartId = -1;
            mainCards.show(mainPanel, &quot;LOGIN&quot;);
        });

        return customerHomeRoot;
    }

    void refreshCustomerHome() {
        welcomeLbl.setText(&quot;  Logged in as: &quot; + loggedInName);
        loadProducts(null);
        loadCart();
        loadOrderHistory();
        loadProfile();
    }
    JPanel buildProductBrowseTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(lbl(&quot;Search:&quot;));
        searchField = field(18);
        top.add(searchField);
        JButton searchBtn = btn(&quot;Search&quot;);
        JButton clearBtn  = outlineBtn(&quot;Show All&quot;);
        top.add(searchBtn); top.add(clearBtn);
        root.add(top, BorderLayout.NORTH);

        productModel = tableModel(&quot;ID&quot;, &quot;Product Name&quot;, &quot;Category&quot;, &quot;Price (Rs)&quot;, &quot;Stock&quot;, &quot;Description&quot;);
        JTable t = makeTable(productModel);
        root.add(scroll(t), BorderLayout.CENTER);

        JPanel bottom = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        bottom.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        JTextField qtyField = field(4);
        qtyField.setText(&quot;1&quot;);
        bottom.add(lbl(&quot;Qty:&quot;));
        bottom.add(qtyField);
        JButton addCartBtn = btn(&quot;Add to Cart&quot;);
        bottom.add(addCartBtn);
        root.add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -&gt; loadProducts(searchField.getText().trim()));
        clearBtn.addActionListener(e -&gt; { searchField.setText(&quot;&quot;); loadProducts(null); });

        // ── NO stock check here — cart is just a wishlist until checkout ──
        addCartBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a product first.&quot;); return; }
            int qty;
            try { qty = Integer.parseInt(qtyField.getText().trim()); }
            catch (NumberFormatException ex) { showErr(&quot;Enter a valid quantity.&quot;); return; }
            if (qty &lt; 1) { showErr(&quot;Quantity must be at least 1.&quot;); return; }

            int productId = (int) productModel.getValueAt(row, 0);
            addToCart(productId, qty);
        });

        return root;
    }

    void loadProducts(String search) {
        productModel.setRowCount(0);
        try {
            String sql = &quot;SELECT product_id, product_name, category, price, stock_quantity, description FROM products_4&quot;;
            if (search != null &amp;&amp; !search.isEmpty())
                sql += &quot; WHERE LOWER(product_name) LIKE LOWER(?) OR LOWER(category) LIKE LOWER(?)&quot;;
            sql += &quot; ORDER BY product_id&quot;;
            PreparedStatement ps = con.prepareStatement(sql);
            if (search != null &amp;&amp; !search.isEmpty()) {
                ps.setString(1, &quot;%&quot; + search + &quot;%&quot;);
                ps.setString(2, &quot;%&quot; + search + &quot;%&quot;);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productModel.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getDouble(4), rs.getInt(5), rs.getString(6)});
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    void addToCart(int productId, int qty) {
        try {
            PreparedStatement chk = con.prepareStatement(
                &quot;SELECT cart_item_id, quantity FROM cart_items_4 WHERE cart_id=? AND product_id=?&quot;);
            chk.setInt(1, currentCartId); chk.setInt(2, productId);
            ResultSet rs = chk.executeQuery();
            if (rs.next()) {
                int newQty = rs.getInt(2) + qty;
                int itemId = rs.getInt(1);
                rs.close(); chk.close();
                PreparedStatement upd = con.prepareStatement(
                    &quot;UPDATE cart_items_4 SET quantity=? WHERE cart_item_id=?&quot;);
                upd.setInt(1, newQty); upd.setInt(2, itemId);
                upd.executeUpdate(); upd.close();
            } else {
                rs.close(); chk.close();
                PreparedStatement ins = con.prepareStatement(
                    &quot;INSERT INTO cart_items_4(cart_id, product_id, quantity) VALUES(?,?,?)&quot;);
                ins.setInt(1, currentCartId); ins.setInt(2, productId); ins.setInt(3, qty);
                ins.executeUpdate(); ins.close();
            }
            showMsg(&quot;Product added to cart.&quot;);
            loadCart();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    DefaultTableModel cartItemsModel;

    JPanel buildCartTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel title = heading(&quot;Shopping Cart&quot;);
        JPanel titlePanel = whitePanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(title);
        root.add(titlePanel, BorderLayout.NORTH);

        cartItemsModel = tableModel(&quot;Cart Item ID&quot;, &quot;Product&quot;, &quot;Price (Rs)&quot;, &quot;Qty&quot;, &quot;Subtotal&quot;);
        JTable t = makeTable(cartItemsModel);
        root.add(scroll(t), BorderLayout.CENTER);

        JPanel bottom = whitePanel(new BorderLayout());
        bottom.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel actions = whitePanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField qtyEdit = field(4);
        JButton updateBtn   = outlineBtn(&quot;Update Qty&quot;);
        JButton removeBtn   = outlineBtn(&quot;Remove Item&quot;);
        JButton checkoutBtn = btn(&quot;Proceed to Checkout&quot;);
        actions.add(lbl(&quot;New Qty:&quot;)); actions.add(qtyEdit);
        actions.add(updateBtn); actions.add(removeBtn);
        actions.add(checkoutBtn);
        bottom.add(actions, BorderLayout.WEST);

        cartModel = tableModel();
        root.add(bottom, BorderLayout.SOUTH);

        // ── NO stock check here — allow any quantity, stock checked only at checkout ──
        updateBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a cart item.&quot;); return; }
            int itemId = (int) cartItemsModel.getValueAt(row, 0);
            int qty;
            try { qty = Integer.parseInt(qtyEdit.getText().trim()); }
            catch (NumberFormatException ex) { showErr(&quot;Enter valid quantity.&quot;); return; }
            if (qty &lt; 1) { showErr(&quot;Quantity must be at least 1.&quot;); return; }

            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE cart_items_4 SET quantity=? WHERE cart_item_id=?&quot;);
                ps.setInt(1, qty); ps.setInt(2, itemId);
                ps.executeUpdate(); ps.close();
                loadCart();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        removeBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select a cart item.&quot;); return; }
            int itemId = (int) cartItemsModel.getValueAt(row, 0);
            if (!confirm(&quot;Remove this item from cart?&quot;)) return;
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;DELETE FROM cart_items_4 WHERE cart_item_id=?&quot;);
                ps.setInt(1, itemId); ps.executeUpdate(); ps.close();
                loadCart();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        checkoutBtn.addActionListener(e -&gt; doCheckout());

        return root;
    }

    void loadCart() {
        if (cartItemsModel == null) return;
        cartItemsModel.setRowCount(0);
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT ci.cart_item_id, p.product_name, p.price, ci.quantity, (p.price*ci.quantity) &quot; +
                &quot;FROM cart_items_4 ci JOIN products_4 p ON ci.product_id=p.product_id &quot; +
                &quot;WHERE ci.cart_id=? ORDER BY ci.cart_item_id&quot;);
            ps.setInt(1, currentCartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItemsModel.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getDouble(3),
                    rs.getInt(4), rs.getDouble(5)});
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    // ── Checkout: stock is validated HERE, right before placing the order ──
    void doCheckout() {
        if (cartItemsModel.getRowCount() == 0) { showErr(&quot;Your cart is empty.&quot;); return; }

              try {
            PreparedStatement stockChk = con.prepareStatement(
                &quot;SELECT p.product_name, p.stock_quantity, ci.quantity &quot; +
                &quot;FROM cart_items_4 ci JOIN products_4 p ON ci.product_id = p.product_id &quot; +
                &quot;WHERE ci.cart_id = ?&quot;);
            stockChk.setInt(1, currentCartId);
            ResultSet rs = stockChk.executeQuery();
            StringBuilder insufficientItems = new StringBuilder();
            while (rs.next()) {
                String productName  = rs.getString(1);
                int    stockAvail   = rs.getInt(2);
                int    cartQty      = rs.getInt(3);
                if (cartQty &gt; stockAvail) {
                    insufficientItems.append(String.format(
                        &quot;• %s — Requested: %d, Available: %d%n&quot;,
                        productName, cartQty, stockAvail));
                }
            }
            rs.close(); stockChk.close();

            if (insufficientItems.length() &gt; 0) {
                showErr(&quot;Cannot place order. Insufficient stock for:\n\n&quot; + insufficientItems +
                        &quot;\nPlease update your cart quantities and try again.&quot;);
                return;
            }
        } catch (SQLException ex) { showErr(ex.getMessage()); return; }

        double total = 0;
        for (int i = 0; i &lt; cartItemsModel.getRowCount(); i++)
            total += (double) cartItemsModel.getValueAt(i, 4);

        String confirmMsg = String.format(&quot;Order Total: Rs %.2f\n\nConfirm purchase?&quot;, total);
        if (!confirm(confirmMsg)) return;

        try {
            PreparedStatement ords = con.prepareStatement(
                &quot;INSERT INTO orders_4(customer_id, order_date, total_amount, order_status) VALUES(?,SYSDATE,?,&apos;Pending&apos;)&quot;,
                new String[]{&quot;order_id&quot;});
            ords.setInt(1, loggedInCustomerId);
            ords.setDouble(2, total);
            ords.executeUpdate();
            ResultSet keys = ords.getGeneratedKeys();
            int orderId = -1;
            if (keys.next()) orderId = keys.getInt(1);
            keys.close(); ords.close();

            PreparedStatement itemPs = con.prepareStatement(
                &quot;SELECT ci.product_id, p.price, ci.quantity FROM cart_items_4 ci &quot; +
                &quot;JOIN products_4 p ON ci.product_id=p.product_id WHERE ci.cart_id=?&quot;);
            itemPs.setInt(1, currentCartId);
            ResultSet itemRs = itemPs.executeQuery();

            PreparedStatement insItem = con.prepareStatement(
                &quot;INSERT INTO order_items_4(order_id, product_id, quantity, price) VALUES(?,?,?,?)&quot;);
            PreparedStatement updStock = con.prepareStatement(
                &quot;UPDATE products_4 SET stock_quantity = stock_quantity - ? WHERE product_id=?&quot;);

            while (itemRs.next()) {
                int pid = itemRs.getInt(1);
                double price = itemRs.getDouble(2);
                int qty = itemRs.getInt(3);
                insItem.setInt(1, orderId); insItem.setInt(2, pid);
                insItem.setInt(3, qty); insItem.setDouble(4, price);
                insItem.executeUpdate();
                updStock.setInt(1, qty); updStock.setInt(2, pid);
                updStock.executeUpdate();
            }
            itemRs.close(); itemPs.close(); insItem.close(); updStock.close();

            PreparedStatement clr = con.prepareStatement(&quot;DELETE FROM cart_items_4 WHERE cart_id=?&quot;);
            clr.setInt(1, currentCartId); clr.executeUpdate(); clr.close();

            showMsg(&quot;Order placed successfully. Order ID: &quot; + orderId);
            loadCart();
            loadOrderHistory();
            loadProducts(null);
            customerTabs.setSelectedIndex(2);
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    JPanel buildOrderHistoryTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(14, 16, 14, 16));

        orderHistoryModel = tableModel(&quot;Order ID&quot;, &quot;Date&quot;, &quot;Total (Rs)&quot;, &quot;Status&quot;);
        JTable masterTable = makeTable(orderHistoryModel);

        DefaultTableModel itemDetailModel = tableModel(&quot;Product&quot;, &quot;Qty&quot;, &quot;Price&quot;, &quot;Subtotal&quot;);
        JTable detailTable = makeTable(itemDetailModel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll(masterTable), scroll(detailTable));
        split.setDividerLocation(280);
        split.setResizeWeight(0.6);
        root.add(split, BorderLayout.CENTER);

        JPanel topRow = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        topRow.add(heading(&quot;Order History&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        topRow.add(refreshBtn);
        root.add(topRow, BorderLayout.NORTH);

        JLabel detailLbl = lbl(&quot;Select an order above to see its items.&quot;);
        root.add(detailLbl, BorderLayout.SOUTH);

        masterTable.getSelectionModel().addListSelectionListener(e -&gt; {
            int row = masterTable.getSelectedRow();
            if (row &lt; 0) return;
            int orderId = (int) orderHistoryModel.getValueAt(row, 0);
            itemDetailModel.setRowCount(0);
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;SELECT p.product_name, oi.quantity, oi.price, (oi.quantity*oi.price) &quot; +
                    &quot;FROM order_items_4 oi JOIN products_4 p ON oi.product_id=p.product_id &quot; +
                    &quot;WHERE oi.order_id=?&quot;);
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    itemDetailModel.addRow(new Object[]{rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)});
                rs.close(); ps.close();
                detailLbl.setText(&quot;Items for Order #&quot; + orderId);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        refreshBtn.addActionListener(e -&gt; loadOrderHistory());
        return root;
    }

    void loadOrderHistory() {
        if (orderHistoryModel == null) return;
        orderHistoryModel.setRowCount(0);
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT order_id, order_date, total_amount, order_status FROM orders_4 &quot; +
                &quot;WHERE customer_id=? ORDER BY order_id DESC&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                orderHistoryModel.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4)});
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    JTextField pfName, pfPhone, pfAddr;

    JPanel buildProfileTab() {
        JPanel root = whitePanel(new BorderLayout());
        root.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel form = whitePanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(24, 32, 24, 32)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = heading(&quot;My Profile&quot;);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; form.add(title, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; g.weightx = 0; form.add(lbl(&quot;Name:&quot;), g);
        pfName = field(20); g.gridx = 1; g.weightx = 1; form.add(pfName, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0; form.add(lbl(&quot;Phone:&quot;), g);
        pfPhone = field(20); g.gridx = 1; g.weightx = 1; form.add(pfPhone, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0; form.add(lbl(&quot;Address:&quot;), g);
        pfAddr = field(20); g.gridx = 1; g.weightx = 1; form.add(pfAddr, g);

        JButton saveBtn = btn(&quot;Save Changes&quot;);
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.WEST;
        form.add(saveBtn, g);

        JPanel wrap = whitePanel(new FlowLayout(FlowLayout.LEFT));
        wrap.add(form);
        root.add(wrap, BorderLayout.NORTH);

        saveBtn.addActionListener(e -&gt; {
            String name  = pfName.getText().trim();
            String phone = pfPhone.getText().trim();
            String addr  = pfAddr.getText().trim();
            if (name.isEmpty()) { showErr(&quot;Name cannot be empty.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE customers_4 SET name=?, phone=?, address=? WHERE customer_id=?&quot;);
                ps.setString(1, name);
                ps.setString(2, phone.isEmpty() ? null : phone);
                ps.setString(3, addr.isEmpty() ? null : addr);
                ps.setInt(4, loggedInCustomerId);
                ps.executeUpdate(); ps.close();
                loggedInName = name;
                welcomeLbl.setText(&quot;  Logged in as: &quot; + loggedInName);
                showMsg(&quot;Profile updated.&quot;);
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        return root;
    }

    void loadProfile() {
        if (pfName == null) return;
        try {
            PreparedStatement ps = con.prepareStatement(
                &quot;SELECT name, phone, address FROM customers_4 WHERE customer_id=?&quot;);
            ps.setInt(1, loggedInCustomerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pfName.setText(rs.getString(1) == null ? &quot;&quot; : rs.getString(1));
                pfPhone.setText(rs.getString(2) == null ? &quot;&quot; : rs.getString(2));
                pfAddr.setText(rs.getString(3) == null ? &quot;&quot; : rs.getString(3));
            }
            rs.close(); ps.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
        JPanel buildAdminPanel() {
        JPanel root = whitePanel(new BorderLayout());

        JPanel topBar = grayPanel(new BorderLayout());
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        JPanel topLeft = grayPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topLeft.add(heading(&quot;Admin Dashboard&quot;));
        topBar.add(topLeft, BorderLayout.WEST);
        JPanel topRight = grayPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        JButton logoutBtn = outlineBtn(&quot;Logout&quot;);
        topRight.add(logoutBtn);
        topBar.add(topRight, BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font(&quot;SansSerif&quot;, Font.PLAIN, 13));
        tabs.addTab(&quot;Products&quot;,  buildAdminProductsTab());
        tabs.addTab(&quot;Orders&quot;,    buildAdminOrdersTab());
        tabs.addTab(&quot;Customers&quot;, buildAdminCustomersTab());
        root.add(tabs, BorderLayout.CENTER);

        logoutBtn.addActionListener(e -&gt; mainCards.show(mainPanel, &quot;LOGIN&quot;));
        return root;
    }
    DefaultTableModel adminProdModel;

    JPanel buildAdminProductsTab() {
        JPanel root = whitePanel(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        String[] lbls   = {&quot;Product ID&quot;, &quot;Product Name&quot;, &quot;Category&quot;, &quot;Price (Rs)&quot;, &quot;Stock Qty&quot;, &quot;Description&quot;};
        JTextField[] flds = new JTextField[6];
        JPanel form = whitePanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(16, 16, 16, 16)));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 6, 5, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i &lt; lbls.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; form.add(lbl(lbls[i]), g);
            flds[i] = field(16); g.gridx = 1; g.weightx = 1; form.add(flds[i], g);
        }

        JButton viewBtn   = btn(&quot;View All&quot;);
        JButton insertBtn = outlineBtn(&quot;Insert&quot;);
        JButton updateBtn = outlineBtn(&quot;Update&quot;);
        JButton deleteBtn = outlineBtn(&quot;Delete&quot;);
        JButton clearBtn  = outlineBtn(&quot;Clear&quot;);

        JPanel btnRow = whitePanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnRow.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        btnRow.add(viewBtn); btnRow.add(insertBtn); btnRow.add(updateBtn);
        btnRow.add(deleteBtn); btnRow.add(clearBtn);

        JPanel left = whitePanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER);
        left.add(btnRow, BorderLayout.SOUTH);
        left.setPreferredSize(new Dimension(290, 0));

        adminProdModel = tableModel(&quot;ID&quot;, &quot;Name&quot;, &quot;Category&quot;, &quot;Price&quot;, &quot;Stock&quot;, &quot;Description&quot;);
        JTable t = makeTable(adminProdModel);
        t.getSelectionModel().addListSelectionListener(e -&gt; {
            int row = t.getSelectedRow(); if (row &lt; 0) return;
            for (int i = 0; i &lt; flds.length; i++)
                flds[i].setText(adminProdModel.getValueAt(row, i) == null ? &quot;&quot; : adminProdModel.getValueAt(row, i).toString());
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, scroll(t));
        split.setDividerLocation(290);
        split.setDividerSize(1);
        root.add(split, BorderLayout.CENTER);

        viewBtn.addActionListener(e -&gt; loadAdminProducts());

        insertBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank() || flds[1].getText().isBlank()) { showErr(&quot;ID and Name required.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;INSERT INTO products_4(product_id,product_name,category,price,stock_quantity,description) VALUES(?,?,?,?,?,?)&quot;);
                ps.setInt(1, Integer.parseInt(flds[0].getText().trim()));
                ps.setString(2, flds[1].getText().trim());
                ps.setString(3, flds[2].getText().isEmpty() ? null : flds[2].getText().trim());
                ps.setDouble(4, flds[3].getText().isEmpty() ? 0 : Double.parseDouble(flds[3].getText().trim()));
                ps.setInt(5, flds[4].getText().isEmpty() ? 0 : Integer.parseInt(flds[4].getText().trim()));
                ps.setString(6, flds[5].getText().isEmpty() ? null : flds[5].getText().trim());
                ps.executeUpdate(); ps.close();
                showMsg(&quot;Product inserted.&quot;); loadAdminProducts();
                for (JTextField f : flds) f.setText(&quot;&quot;);
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        updateBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank()) { showErr(&quot;Select a product first.&quot;); return; }
            try {
                PreparedStatement ps = con.prepareStatement(
                    &quot;UPDATE products_4 SET product_name=?,category=?,price=?,stock_quantity=?,description=? WHERE product_id=?&quot;);
                ps.setString(1, flds[1].getText().trim());
                ps.setString(2, flds[2].getText().isEmpty() ? null : flds[2].getText().trim());
                ps.setDouble(3, flds[3].getText().isEmpty() ? 0 : Double.parseDouble(flds[3].getText().trim()));
                ps.setInt(4, flds[4].getText().isEmpty() ? 0 : Integer.parseInt(flds[4].getText().trim()));
                ps.setString(5, flds[5].getText().isEmpty() ? null : flds[5].getText().trim());
                ps.setInt(6, Integer.parseInt(flds[0].getText().trim()));
                if (ps.executeUpdate() &gt; 0) { showMsg(&quot;Product updated.&quot;); loadAdminProducts(); } else showErr(&quot;ID not found.&quot;);
                ps.close();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        deleteBtn.addActionListener(e -&gt; {
            if (flds[0].getText().isBlank()) { showErr(&quot;Select a product.&quot;); return; }
            if (!confirm(&quot;Delete product #&quot; + flds[0].getText() + &quot;?&quot;)) return;
            try {
                PreparedStatement ps = con.prepareStatement(&quot;DELETE FROM products_4 WHERE product_id=?&quot;);
                ps.setInt(1, Integer.parseInt(flds[0].getText().trim()));
                if (ps.executeUpdate() &gt; 0) { showMsg(&quot;Deleted.&quot;); loadAdminProducts(); for (JTextField f : flds) f.setText(&quot;&quot;); }
                else showErr(&quot;Not found.&quot;);
                ps.close();
            } catch (Exception ex) { showErr(ex.getMessage()); }
        });

        clearBtn.addActionListener(e -&gt; { for (JTextField f : flds) f.setText(&quot;&quot;); });

        SwingUtilities.invokeLater(this::loadAdminProducts);
        return root;
    }

    void loadAdminProducts() {
        if (adminProdModel == null) return;
        adminProdModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT product_id,product_name,category,price,stock_quantity,description FROM products_4 ORDER BY product_id&quot;);
            while (rs.next())
                adminProdModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getDouble(4),rs.getInt(5),rs.getString(6)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }
    DefaultTableModel adminOrderModel;

    JPanel buildAdminOrdersTab() {
        JPanel root = whitePanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(heading(&quot;Order Management&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        top.add(refreshBtn);
        root.add(top, BorderLayout.NORTH);

        adminOrderModel = tableModel(&quot;Order ID&quot;, &quot;Customer&quot;, &quot;Date&quot;, &quot;Total (Rs)&quot;, &quot;Status&quot;);
        JTable t = makeTable(adminOrderModel);

        JComboBox&lt;String&gt; statusBox = new JComboBox&lt;&gt;(new String[]{&quot;Pending&quot;, &quot;Shipped&quot;, &quot;Delivered&quot;});
        JButton updateBtn = btn(&quot;Update Status&quot;);
        JPanel bot = whitePanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        bot.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        bot.add(lbl(&quot;Set Status:&quot;));
        bot.add(statusBox);
        bot.add(updateBtn);

        root.add(scroll(t), BorderLayout.CENTER);
        root.add(bot, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -&gt; loadAdminOrders());
        updateBtn.addActionListener(e -&gt; {
            int row = t.getSelectedRow();
            if (row &lt; 0) { showErr(&quot;Select an order.&quot;); return; }
            int orderId = (int) adminOrderModel.getValueAt(row, 0);
            String status = (String) statusBox.getSelectedItem();
            try {
                PreparedStatement ps = con.prepareStatement(&quot;UPDATE orders_4 SET order_status=? WHERE order_id=?&quot;);
                ps.setString(1, status); ps.setInt(2, orderId);
                ps.executeUpdate(); ps.close();
                showMsg(&quot;Order #&quot; + orderId + &quot; status updated to &quot; + status);
                loadAdminOrders();
            } catch (SQLException ex) { showErr(ex.getMessage()); }
        });

        SwingUtilities.invokeLater(this::loadAdminOrders);
        return root;
    }

    void loadAdminOrders() {
        if (adminOrderModel == null) return;
        adminOrderModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT o.order_id, c.name, o.order_date, o.total_amount, o.order_status &quot; +
                &quot;FROM orders_4 o LEFT JOIN customers_4 c ON o.customer_id=c.customer_id ORDER BY o.order_id DESC&quot;);
            while (rs.next())
                adminOrderModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    DefaultTableModel adminCustModel;

    JPanel buildAdminCustomersTab() {
        JPanel root = whitePanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel top = whitePanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        top.add(heading(&quot;Customer Records&quot;));
        JButton refreshBtn = outlineBtn(&quot;Refresh&quot;);
        top.add(refreshBtn);
        root.add(top, BorderLayout.NORTH);

        adminCustModel = tableModel(&quot;ID&quot;, &quot;Name&quot;, &quot;Email&quot;, &quot;Phone&quot;, &quot;Address&quot;, &quot;Registered&quot;);
        JTable t = makeTable(adminCustModel);
        root.add(scroll(t), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -&gt; loadAdminCustomers());
        SwingUtilities.invokeLater(this::loadAdminCustomers);
        return root;
    }

    void loadAdminCustomers() {
        if (adminCustModel == null) return;
        adminCustModel.setRowCount(0);
        try {
            ResultSet rs = con.createStatement().executeQuery(
                &quot;SELECT customer_id,name,email,phone,address,created_date FROM customers_4 ORDER BY customer_id&quot;);
            while (rs.next())
                adminCustModel.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6)});
            rs.close();
        } catch (SQLException ex) { showErr(ex.getMessage()); }
    }

    static void connectDB() {
        try {
            Class.forName(&quot;oracle.jdbc.driver.OracleDriver&quot;);
            con = DriverManager.getConnection(
                &quot;jdbc:oracle:thin:@localhost:1521:xe&quot;, &quot;system&quot;, &quot;np835835&quot;);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                &quot;DB Connection Failed:\n&quot; + e.getMessage(), &quot;Error&quot;, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hacrr::new);
    }
}include &lt;vector&gt;
</pre>

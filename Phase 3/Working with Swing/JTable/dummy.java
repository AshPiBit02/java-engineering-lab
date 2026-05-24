salesBtn = new JButton("salesBtn") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        salesBtn.setBounds(100, 170, 200, 30);
        salesBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        salesBtn.setBackground(new Color(0, 113, 234, 60));
        salesBtn.setForeground(Color.WHITE);
        salesBtn.setOpaque(false);
        salesBtn.setContentAreaFilled(false);
        salesBtn.setFocusable(false);
        salesBtn.setFocusPainted(false);
        salesBtn.setBorderPainted(false);

        salesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 60));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 120));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                salesBtn.setBackground(new Color(0, 113, 234, 60));
            }
        });
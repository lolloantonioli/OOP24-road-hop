package it.unibo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unibo.model.Shop.api.Skin;
/**
 * ShopView class that represents the shop interface in the game.
 * It displays a list of skins available for purchase or selection,
 * along with the player's current coin balance and a back button to return to the main menu.
 */
public class ShopView extends JPanel {

    private JLabel titleLabel;
    private JLabel coinsLabel; 
    private JButton backButton;
    private final JPanel skinsPanel;
    private final JScrollPane scrollPane;

    private List<Skin> skins = new ArrayList<>();
    private Runnable onBackToMainMenu;
    private BiConsumer<String, Integer> onSkinPurchase;
    private Consumer<String> onSkinSelected;

<<<<<<< HEAD
    private final float headerFontScale = 1.5f;
    private final float nameLabelFontScale = 1.2f;
    private final int cardBorderPadding = 5;
    private final int cardWidth = 120;
    private final int cardHeight = 160;
    private final int squareDimension = 48;
=======
    private final static float HEADER_FONT_SCALE = 1.5f;
    private final static float NAME_LABEL_FONT_SCALE = 1.2f;
    private final static int CARD_BORDER_PADDING = 5;
    private final static int CARD_WIDTH = 120;
    private final static int CARD_HEIGHT = 160;
    private final static int SQUARE_DIMENSION = 48;
>>>>>>> ef1a682268bc7c28b9e2ef8f7ee55dfda1a05157

    /**
     * Constructs a ShopView with a header, skins display area, and footer.
     * The header contains the shop title and coin balance,
     * the skins area displays available skins,
     * and the footer contains a back button to return to the main menu.
     */
    public ShopView() {
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        final JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        skinsPanel = new JPanel();
        skinsPanel.setLayout(new GridLayout(0, 3));
        skinsPanel.setBackground(Color.BLUE);

        scrollPane = new JScrollPane(skinsPanel);
        scrollPane.setBackground(Color.BLUE);
        scrollPane.getViewport().setBackground(Color.BLUE);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        final JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

    }


    private JPanel createHeaderPanel() {
        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Skin Shop", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC, titleLabel.getFont().getSize() * headerFontScale));

        coinsLabel = new JLabel("Coins: 0", SwingConstants.RIGHT);
        coinsLabel.setForeground(Color.YELLOW);
        coinsLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC, titleLabel.getFont().getSize() * headerFontScale));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(coinsLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFooterPanel() {
        final JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.BLUE);

        backButton = new JButton("Back");
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());

        backButton.setFont(backButton.getFont().deriveFont(Font.ROMAN_BASELINE));

        backButton.addActionListener(e -> {
            if (onBackToMainMenu != null) {
                onBackToMainMenu.run();
            }
        });

        footerPanel.add(backButton);

        return footerPanel;
    }

    private void refreshSkins() {
        skinsPanel.removeAll();
        for (final Skin skin : skins) {
            skinsPanel.add(createSkinCard(skin));
        }
        skinsPanel.revalidate();
        skinsPanel.repaint();
    }


private Component createSkinCard(final Skin skin) {
        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.CYAN);

        // Different border colors based on skin status
        final Color borderColor;
        if (skin.isSelected()) {
            borderColor = Color.CYAN; // Selected skin
        } else if (skin.isUnlocked()) {
            borderColor = Color.GREEN; // Owned but not selected
        } else {
            borderColor = Color.GRAY; // Not owned
        }

        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            BorderFactory.createEmptyBorder(cardBorderPadding, cardBorderPadding, cardBorderPadding, cardBorderPadding)
        ));

        // Skin preview: colored square
        final JPanel colorPreview = new JPanel();
        colorPreview.setBackground(skin.getColor());
        colorPreview.setMaximumSize(new Dimension(squareDimension, squareDimension));
        colorPreview.setPreferredSize(new Dimension(squareDimension, squareDimension));
        colorPreview.setMinimumSize(new Dimension(squareDimension, squareDimension));
        colorPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        card.add(colorPreview);
        card.add(Box.createVerticalStrut(8));

        // Skin name
        final JLabel nameLabel = new JLabel(skin.getName(), SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, nameLabel.getFont().getSize() * nameLabelFontScale));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(8));

        // Bottone acquista/equip
        final JButton actionButton = new JButton();
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (skin.isSelected()) {
            actionButton.setText("EQUIPPED");
            actionButton.setEnabled(false);
            actionButton.setBackground(Color.CYAN);
            actionButton.setForeground(Color.BLACK);
        } else if (skin.isUnlocked()) {
            actionButton.setText("EQUIP");
            actionButton.setEnabled(true);
            actionButton.setBackground(Color.GREEN);
            actionButton.setForeground(Color.BLACK);
            actionButton.addActionListener(e -> {
                if (onSkinSelected != null) {
                    onSkinSelected.accept(skin.getId());
                }
            });
        } else {
            actionButton.setText("BUY: " + skin.getPrice());
            actionButton.setEnabled(true);
            actionButton.setBackground(Color.YELLOW);
            actionButton.setForeground(Color.BLACK);
            actionButton.addActionListener(e -> {
                if (onSkinPurchase != null) {
                    onSkinPurchase.accept(skin.getId(), skin.getPrice());
                }
            });
        }
        card.add(actionButton);
        card.setMaximumSize(new Dimension(cardWidth, cardHeight));
        card.setPreferredSize(new Dimension(cardWidth, cardHeight));
        card.setMinimumSize(new Dimension(cardWidth, cardHeight));

        return card;
    }

    /**
     * Sets the current coin balance and updates the coins label.
     * Also refreshes the skins display to reflect any changes in skin ownership.
     *
     * @param coins the new coin balance
     */
    public final void setCoins(final int coins) {
       coinsLabel.setText("Coins: " + coins);
       refreshSkins();
    }

    /**
     * Sets the action to be performed when the back button is clicked.
     *
     * @param onBackToMainMenu the action to perform when the back button is clicked
     */
    public final void setOnBackToMainMenu(final Runnable onBackToMainMenu) {
        this.onBackToMainMenu = onBackToMainMenu;
    }

    /**
     * Sets the list of skins available in the shop and refreshes the display.
     *
     * @param skins the list of skins to display
     */
    public final void setSkins(final List<Skin> skins) {
        this.skins = new ArrayList<>(skins);
        refreshSkins();
    }


    /**
     * Sets the action to be performed when a skin is purchased.
     *
     * @param skinPurchase the action to perform when a skin is purchased, accepting skin ID and price
     */
    public final void setOnSkinPurchase(final BiConsumer<String, Integer> skinPurchase) {
        this.onSkinPurchase = skinPurchase;
    }


    /**
     * Sets the action to be performed when a skin is selected.
     *
     * @param onSkinSelected the action to perform when a skin is selected, accepting skin ID
     */
    public final void setOnSkinSelected(final Consumer<String> onSkinSelected) {
        this.onSkinSelected = onSkinSelected;
    }

}

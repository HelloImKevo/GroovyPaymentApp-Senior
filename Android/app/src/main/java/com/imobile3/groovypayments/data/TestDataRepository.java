package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.utils.CartBuilder;
import com.imobile3.groovypayments.data.utils.CartProductBuilder;
import com.imobile3.groovypayments.data.utils.CartTaxBuilder;
import com.imobile3.groovypayments.data.utils.ProductBuilder;
import com.imobile3.groovypayments.data.utils.TaxBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TestDataRepository {

    public enum Environment {
        InstrumentationTest,
        GroovyDemo
    }

    //region Singleton Implementation

    private static TestDataRepository sInstance;

    private TestDataRepository() {
        init();
    }

    @NonNull
    public static synchronized TestDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new TestDataRepository();
        }
        return sInstance;
    }

    //endregion

    private Random mRandom;

    private String[] mRandomItemNames;
    private String[] mRandomEmailAddresses;
    private String[] mRandomDiscountNames;
    private String[] mRandomJobNames;
    private String[] mRandomTaxNames;
    private String[] mRandomFirstNames;
    private String[] mRandomLastNames;
    private String[] mRandomStreetAddresses;
    private String[] mRandomPhoneNumbers;
    private String[] mRandomBarcodes;

    //region Getters

    public String getRandomItemName() {
        int randIndex = mRandom.nextInt(mRandomItemNames.length);
        return mRandomItemNames[randIndex];
    }

    public String getRandomEmailAddress() {
        int randIndex = mRandom.nextInt(mRandomEmailAddresses.length);
        return mRandomEmailAddresses[randIndex];
    }

    public String getRandomDiscountName() {
        int randIndex = mRandom.nextInt(mRandomDiscountNames.length);
        return mRandomDiscountNames[randIndex];
    }

    public String getRandomJobName() {
        int randIndex = mRandom.nextInt(mRandomJobNames.length);
        return mRandomJobNames[randIndex];
    }

    public String getRandomTaxName() {
        int randIndex = mRandom.nextInt(mRandomTaxNames.length);
        return mRandomTaxNames[randIndex];
    }

    public String getRandomFirstName() {
        int randIndex = mRandom.nextInt(mRandomFirstNames.length);
        return mRandomFirstNames[randIndex];
    }

    public String getRandomLastName() {
        int randIndex = mRandom.nextInt(mRandomLastNames.length);
        return mRandomLastNames[randIndex];
    }

    public String getRandomFullName() {
        return getRandomFirstName() + " " + getRandomLastName();
    }

    public String getRandomStreetAddress() {
        int randIndex = mRandom.nextInt(mRandomStreetAddresses.length);
        return mRandomStreetAddresses[randIndex];
    }

    public String getRandomPhoneNumber() {
        int randIndex = mRandom.nextInt(mRandomPhoneNumbers.length);
        return mRandomPhoneNumbers[randIndex];
    }

    public String getRandomBarcode() {
        int randIndex = mRandom.nextInt(mRandomBarcodes.length);
        return mRandomBarcodes[randIndex];
    }

    /**
     * @return Random {@link Date} within the last 2 weeks.
     */
    public Date getRandomDate() {
        long maxMillis = new Date().getTime();
        long minMillis = maxMillis - 1300000000;
        return new Date(getRandomLongRanged(minMillis, maxMillis));
    }

    public long getRandomLongRanged(long min, long max) {
        long difference = (max - min) + 1;
        return min + (long)(Math.random() * difference);
    }

    /**
     * @return Random integer between the min and max; inclusive of the min and max.
     */
    public int getRandomIntRanged(int min, int max) {
        return mRandom.nextInt((max + 1) - min) + min;
    }

    //endregion

    //region Public Utilities

    private final long CART_ID_1 = 801L;
    private final long CART_ID_2 = 802L;
    private final long CART_ID_3 = 803L;
    private final long CART_ID_4 = 804L;
    private final long CART_ID_5 = 805L;
    private final long CART_ID_6 = 806L;
    private final long CART_ID_7 = 807L;
    private final long CART_ID_8 = 808L;

    @NonNull
    public List<CartEntity> getCarts(@NonNull Environment environment) {
        List<CartEntity> results = new ArrayList<>();

        if (Environment.InstrumentationTest == environment) {
            results.add(CartBuilder.build(201L,
                    new Date(180000000L),
                    900L, 100L, 1000L));

            results.add(CartBuilder.build(202L,
                    new Date(185000000L),
                    700L, 150L, 850L));
        }
        // Generate Point-of-Sale Demo order history.
        else if (Environment.GroovyDemo == environment) {
            results.add(CartBuilder.build(CART_ID_1, new Date(1457040005823L)));
            results.add(CartBuilder.build(CART_ID_2, new Date(1458050008193L)));
            results.add(CartBuilder.build(CART_ID_3, new Date(1459060007655L)));
            results.add(CartBuilder.build(CART_ID_4, new Date(1460070008873L)));
            results.add(CartBuilder.build(CART_ID_5, new Date(1461080007781L)));
            results.add(CartBuilder.build(CART_ID_6, new Date(1462090000058L)));
            results.add(CartBuilder.build(CART_ID_7, new Date(1465042006398L)));
            results.add(CartBuilder.build(CART_ID_8, new Date(1468081004457L)));
        }

        return results;
    }

    @NonNull
    public List<CartProductEntity> getCartProducts(
            @NonNull Environment environment,
            @NonNull CartEntity cart) {
        List<CartProductEntity> results = new ArrayList<>();

        final long cartId = cart.getId();

        if (Environment.InstrumentationTest == environment) {
            results.add(CartProductBuilder.build((cart.getId() * 2) + 1L,
                    cart.getId(),
                    "Test Cheeseburger",
                    500L, 1));

            results.add(CartProductBuilder.build((cart.getId() * 2) + 2L,
                    cart.getId(),
                    "Test Soda",
                    125L, 1));
        }
        // Generate Point-of-Sale Demo order history.
        else if (Environment.GroovyDemo == environment) {
            if (CART_ID_1 == cartId) {
                results.add(CartProductBuilder.build(100002L, cartId,
                        "Cotton Candy", 399L, 1));

                results.add(CartProductBuilder.build(100003L, cartId,
                        "Giant Lollipop", 499L, 1));
            } else if (CART_ID_2 == cartId) {
                results.add(CartProductBuilder.build(100004L, cartId,
                        "Cappuccino", 599L, 1));

                results.add(CartProductBuilder.build(100005L, cartId,
                        "Caramel Macchiato", 750L, 2));
            } else if (CART_ID_3 == cartId) {
                results.add(CartProductBuilder.build(100006L, cartId,
                        "Bacon Cookie", 375L, 2));

                results.add(CartProductBuilder.build(100007L, cartId,
                        "Chocolate Cookie", 125L, 2));
            } else if (CART_ID_4 == cartId) {
                results.add(CartProductBuilder.build(100008L, cartId,
                        "Oatmeal Cookie", 375L, 2));

                results.add(CartProductBuilder.build(100009L, cartId,
                        "Fortune Cookie", 35L, 1));
            } else if (CART_ID_5 == cartId) {
                results.add(CartProductBuilder.build(100010L, cartId,
                        "Cheese Cake Slice", 575L, 1));

                results.add(CartProductBuilder.build(100011L, cartId,
                        "Tiramisu Slice", 625L, 1));
            } else if (CART_ID_6 == cartId) {
                results.add(CartProductBuilder.build(100012L, cartId,
                        "Frozen Yogurt (Cup)", 325L, 1));

                results.add(CartProductBuilder.build(100013L, cartId,
                        "Strawberry Ice Cream (Bowl)", 875L, 1));
            } else if (CART_ID_7 == cartId) {
                results.add(CartProductBuilder.build(100014L, cartId,
                        "Batman Comic Book", 950L, 1));

                results.add(CartProductBuilder.build(100015L, cartId,
                        "Latte (S)", 275L, 1));
            } else if (CART_ID_8 == cartId) {
                results.add(CartProductBuilder.build(100016L, cartId,
                        "Beef Pho", 850L, 1));

                results.add(CartProductBuilder.build(100017L, cartId,
                        "Pork and Tripe Pho", 775L, 1));
            }
        }

        return results;
    }

    @NonNull
    public List<CartTaxEntity> getCartTaxes(
            @NonNull Environment environment,
            @NonNull CartEntity cart) {
        List<CartTaxEntity> results = new ArrayList<>();

        final long cartId = cart.getId();

        if (Environment.InstrumentationTest == environment) {
            results.add(CartTaxBuilder.build((cart.getId() * 2) + 101L,
                    cart.getId(),
                    "Test Tax 2%", "0.02"));

            results.add(CartTaxBuilder.build((cart.getId() * 2) + 102L,
                    cart.getId(),
                    "Test Tax 4%", "0.04"));
        }
        // Generate Point-of-Sale Demo order history.
        else if (Environment.GroovyDemo == environment) {
            results.add(CartTaxBuilder.build((cartId * 2) + 7001L,
                    cartId,
                    "5% Restaurant Tax", "0.05"));

            results.add(CartTaxBuilder.build((cartId * 2) + 7002L,
                    cartId,
                    "7.5% Federal Tax", "0.075"));
        }

        return results;
    }

    @NonNull
    public List<ProductEntity> getProducts(@NonNull Environment environment) {
        List<ProductEntity> results = new ArrayList<>();

        if (Environment.InstrumentationTest == environment) {
            results.add(ProductBuilder.build(1L,
                    "Grilled Chicken Sandwich",
                    "",
                    850L, 200L,
                    GroovyIcon.Sandwich, GroovyColor.Yellow));

            results.add(ProductBuilder.build(2L,
                    "Ultra Rare Pet Rock",
                    "",
                    1499L, 50L,
                    GroovyIcon.Sandwich, GroovyColor.Blue));
        }
        // Generate Point-of-Sale Demo inventory.
        else if (Environment.GroovyDemo == environment) {
            results.add(ProductBuilder.build(101L,
                    "Tasty Chicken Sandwich",
                    "Chicken, lettuce, tomato and mayo",
                    750L, 200L,
                    GroovyIcon.Sandwich, GroovyColor.Yellow));

            results.add(ProductBuilder.build(102L,
                    "10-Pack of AA Batteries",
                    "Medium-quality batteries",
                    899L, 125L,
                    GroovyIcon.BatteryPack, GroovyColor.Gray));

            results.add(ProductBuilder.build(103L,
                    "Metal Folding Chair",
                    "Weighs approximately 12 lbs.",
                    2250L, 400L,
                    GroovyIcon.WoodenChair, GroovyColor.Blue));

            results.add(ProductBuilder.build(104L,
                    "Coffee Mug w/ Custom Design",
                    "Requires at least 48 hours to fulfill custom order",
                    1275L, 300L,
                    GroovyIcon.CoffeeMug, GroovyColor.Red));

            results.add(ProductBuilder.build(105L,
                    "Google I/O Novelty T-Shirt",
                    "Depicts the little green android dude",
                    1750L, 75L,
                    GroovyIcon.TShirt, GroovyColor.Orange));

            results.add(ProductBuilder.build(106L,
                    "Super Nintendo Entertainment System",
                    "The classic SNES console w/ Super Mario World",
                    15000L, 3000L,
                    GroovyIcon.RetroController, GroovyColor.Purple));

            results.add(ProductBuilder.build(107L,
                    "25-Pack of Snickers Candy Bars",
                    "",
                    1500L, 350L,
                    GroovyIcon.WrappedSweet, GroovyColor.Orange));

            results.add(ProductBuilder.build(108L,
                    "Hand-Made Wood-Carved Tiki Mask",
                    "This mask is definitely not cursed",
                    2000L, 400L,
                    GroovyIcon.CeremonialMask, GroovyColor.Red));

            results.add(ProductBuilder.build(109L,
                    "Paper-Mate Gel Pen (0.5)",
                    "",
                    299L, 65L,
                    GroovyIcon.Pencil, GroovyColor.Blue));

            results.add(ProductBuilder.build(110L,
                    "Wagh Bakri Instant Masala Tea",
                    "Simply add boiling water",
                    699L, 125L,
                    GroovyIcon.Teapot, GroovyColor.Yellow));

            results.add(ProductBuilder.build(111L,
                    "Pine Tree Air Freshener",
                    "Great for hanging from a rear-view mirror",
                    199L, 10L,
                    GroovyIcon.PineTree, GroovyColor.Green));

            results.add(ProductBuilder.build(112L,
                    "Can of Tomato Soup",
                    "High-quality preservatives give this a 10-year shelf-life",
                    99L, 15L,
                    GroovyIcon.OpenedFoodCan, GroovyColor.Orange));

            results.add(ProductBuilder.build(113L,
                    "Trail Mix w/ Peanuts",
                    "Also has chocolate candies and raisins",
                    199L, 10L,
                    GroovyIcon.Sandwich, GroovyColor.Orange));

            results.add(ProductBuilder.build(114L,
                    "Claw Hammer",
                    "Used for hammering stuff, like a disposable cell phone",
                    499L, 75L,
                    GroovyIcon.HammerNails, GroovyColor.Purple));

            results.add(ProductBuilder.build(115L,
                    "Phillips Screwdriver",
                    "",
                    125L, 18L,
                    GroovyIcon.HammerNails, GroovyColor.Blue));

            results.add(ProductBuilder.build(116L,
                    "Coca Cola (300 mL)",
                    "",
                    125L, 10L,
                    GroovyIcon.CoffeeMug, GroovyColor.Red));
        }

        return results;
    }

    @NonNull
    public List<TaxEntity> getTaxes(@NonNull Environment environment) {
        TaxEntity result;
        List<TaxEntity> results = new ArrayList<>();

        if (Environment.InstrumentationTest == environment) {
            result = new TaxEntity();
            result.setId(1L);
            result.setName("5% Test Tax");
            result.setRate(new BigDecimal("0.05"));
            results.add(result);

            result = new TaxEntity();
            result.setId(2L);
            result.setName("7.5% Test Tax");
            result.setRate(new BigDecimal("0.075"));
            results.add(result);
        }
        // Generate Point-of-Sale Demo taxes.
        else if (Environment.GroovyDemo == environment) {
            results.add(TaxBuilder.build(201L,
                    "5% Federal Tax", "0.05"));

            results.add(TaxBuilder.build(202L,
                    "7.5% State Tax", "0.075"));
        }

        return results;
    }

    @NonNull
    public List<ProductTaxJunctionEntity> getProductTaxJunctions(
            @NonNull ProductEntity product,
            @Nullable List<TaxEntity> taxes) {
        ProductTaxJunctionEntity result;
        List<ProductTaxJunctionEntity> results = new ArrayList<>();

        if (taxes != null) {
            for (TaxEntity tax : taxes) {
                result = new ProductTaxJunctionEntity();
                result.setProductId(product.getId());
                result.setTaxId(tax.getId());
                results.add(result);
            }
        }

        return results;
    }

    //endregion

    //region Private Functions

    private void init() {
        // We want the generated data to be consistent and predictable.
        final long seed = 100000L;
        mRandom = new Random(seed);
        initStringResources();
    }

    private void initStringResources() {
        mRandomItemNames = new String[] {
                "Artichoke",
                "Arugula",
                "Asparagus",
                "Alfalfa",
                "Black Beans",
                "Cabbage",
                "Potato",
                "Tomato",
                "Broccoli",
                "Kale",
                "Lettuce",
                "Radish",
                "Cucumber",
                "Apple",
                "Banana",
                "Orange",
                "Carrot",
                "Peach",
                "Burger",
                "Coca Cola",
                "Sprite",
                "Orange Juice",
                "Mr. Pibb",
                "RC Cola",
                "Mountain Dew",
                "Turkey Sandwich",
                "Root Beer",
                "Ice Cream Sandwich",
                "Jelly Beans",
                "Gingerbread Cookie",
                "Cupcake Platter",
                "Delicious Eclair",
                "Honey Nut Cheerios",
                "Chianti",
                "Pinot Noir",
                "Riesling",
                "Chardonnay",
                "Merlot",
                "Red Wine",
                "Whiskey",
                "Bourbon",
                "Vodka",
                "Side of Fava Beans",
                "Shrimp Cocktail",
                "Bottle of Unicorn Tears",
                "Ultra Rare Pet Rock",
                "Playstation 3",
                "Playstation 4",
                "Xbox 360",
                "Xbox One",
                "Excalibur",
                "Levi Jeans",
                "Men's T-Shirt",
                "Women's T-Shirt",
                "Big Beef Burger",
                "Grilled Chicken Salad",
                "Turkey Bacon Ranch Wrap",
                "Grilled Cheese Sandwich",
                "Cheese Pizza",
                "BBQ Pizza",
                "Italian Pizza",
                "Hawaiian Pizza",
                "Chicago Style Pizza",
                "Pepperoni Pizza",
                "Supreme Pizza",
                "Mondo Mango Burger"
        };
        mRandomBarcodes = new String[] {
                "00A57-8058473-001",
                "56473-84372BP-091",
                "74872-89GFSQW-908",
                "12HJH-HK2H323-672",
                "326AG-543POU3-786",
                "78VCX-FJMN432-340",
                "67853-FHJK654-234"
        };
        mRandomEmailAddresses = new String[] {
                "bob.landers@gmail.com",
                "luther.graves@hotmail.com",
                "troy.johnson@gmail.com",
                "cindy.adams@yahoo.com",
                "rachel.smith@gmail.com",
                "gail.gehrig@yahoo.com",
                "liz.thomas@yahoo.com",
                "kevin.schmidt@gmail.com",
                "neo@thematrix.com"
        };
        mRandomDiscountNames = new String[] {
                "10% Off",
                "25% Off",
                "40% Off",
                "50% Off",
                "$1 Off",
                "$2 Off",
                "$3 Off",
                "$4 Off",
                "Open Flat Amount",
                "Employee Discount",
                "Happy Hour Discount",
                "VIP Discount"
        };
        mRandomJobNames = new String[] {
                "Manager",
                "Bartender",
                "Cashier",
                "Dragon Slayer",
                "Custodian",
                "General Manager",
                "Shift Manager",
                "Server",
                "Front Desk Clerk",
                "Sales Member",
                "Lifeguard",
                "Computer Engineer",
                "Rogue Wizard",
                "Kung Fu Master",
                "Pit Boss",
                "Receptionist",
                "Human Resources Director",
                "Truck Driver",
                "Waste Water Pump Mechanic"
        };
        mRandomTaxNames = new String[] {
                "1% Tax",
                "2% Tax",
                "3% Tax",
                "4% Tax",
                "5% Tax",
                "10% Tax",
                "15% Tax",
                "20% Tax",
                "25% Tax",
                "Breakfast Tax",
                "Lunch Tax",
                "Dinner Tax",
                "Dessert Tax",
                "Medieval Tax",
                "Harbortouch Tax",
                "Flat Tax",
                "State Tax",
                "Federal Tax"
        };
        mRandomStreetAddresses = new String[] {
                "77 Party Lane",
                "908 Pine Avenue",
                "720 Spaghetti Road",
                "1020 Spaceship Street",
                "901 Jupiter Lane",
                "85 Mars Landing",
                "5562 Woodchuck Boulevard",
                "2420 Saturn Avenue",
                "101 Wizard Drive",
                "1881 Harbortouch Lane",
                "3130 Pleasant Mall",
                "67 Rocky Dale Arbor",
                "9933 Emerald Parade",
                "2996 Wishing Willow Highway",
                "5661 Indian Highlands",
                "916 Iron Leaf Wood",
                "5578 Lazy Forest Line",
                "900 Broad Hollow",
                "1406 Clear Cider Downs"
        };
        mRandomPhoneNumbers = new String[] {
                "404-565-9080",
                "351-882-8907",
                "777-746-9012",
                "867-900-2240",
                "756-990-6464",
                "456-547-7897",
                "787-474-4477",
                "322-515-4545",
                "898-978-1201",
                "215-897-3325",
                "456-898-8895",
                "223-554-9985",
                "898-595-7870",
                "562-562-0202",
                "992-202-3030",
                "633-707-8080",
                "611-787-0606",
                "898-333-4547"
        };
        mRandomFirstNames = new String[] {
                "John",
                "Andrew",
                "Cassie",
                "Bob",
                "William",
                "Matthew",
                "Jennifer",
                "Mitchell",
                "Patrick",
                "Amy",
                "Laurie",
                "Amber",
                "Rebecca",
                "Vince",
                "Greg",
                "Jeff",
                "Lindsay",
                "Michael",
                "Katrina",
                "Kyle",
                "Scott",
                "Tony",
                "Brian",
                "Gail",
                "Hannah",
                "Brad",
                "Jack",
                "Benjamin",
                "Alice",
                "Valerie",
                "Daniel"
        };
        mRandomLastNames = new String[] {
                "Johnson",
                "Thompson",
                "Gentry",
                "Schanz",
                "Salucci",
                "Portune",
                "Greene",
                "Gehrig",
                "Thomas",
                "Williams",
                "Smith",
                "Wallace",
                "Volkov",
                "Swanson",
                "Jones",
                "Brown",
                "Jackson",
                "White",
                "Garcia",
                "Robinson",
                "Clark",
                "Allen",
                "Young",
                "King",
                "Lopez",
                "Adams",
                "Miller",
                "Archer",
                "Phillips",
                "Collins",
                "Rogers",
                "Cook",
                "Murphy",
                "Cooper",
                "Richardson",
                "Sanders",
                "Washington",
                "Barnes",
                "Hughes",
                "Butler",
                "Simmons",
                "Foster"
        };
    }

    //endregion
}

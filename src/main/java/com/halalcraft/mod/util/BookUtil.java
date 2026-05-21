package com.halalcraft.mod.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.Crayons; // Not real, using standard Text.literal
import java.util.ArrayList;
import java.util.List;

public class BookUtil {
    public static ItemStack createInfoBook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);

        List<Text> pages = new ArrayList<>();
        pages.add(Text.literal("§l§6HalalCraft Information\n\nWelcome to the trial. Here you will learn the rules of survival under Islamic law.\n\nWatch the tutorial here:\nhttps://youtu.be/-fMkyL1q0eU"));

        WrittenBookContent content = new WrittenBookContent(
            Text.literal("Thông tin Plugin"),
            Text.literal("HalalCraft"),
            pages
        );

        book.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);
        // Custom tag for removal logic
        book.set(DataComponentTypes.CUSTOM_DATA, net.minecraft.component.type.NbtComponent.create(
            new net.minecraft.nbt.NbtCompound() {{ put("halal_tutorial_book", 1); }}
        ));

        return book;
    }

    public static ItemStack createGuideBook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);

        List<Text> pages = new ArrayList<>();
        pages.add(Text.literal("§l§6Luật Lệ & Hướng Dẫn\n\n1. Shahada: Phải gõ đúng câu Shahada khi vào game để được tự do."));
        pages.add(Text.literal("2. Zakat: Mỗi 3 phút, 50% tài nguyên thô sẽ bị thu thuế (làm tròn lên)."));
        pages.add(Text.literal("3. Halal: Cấm thịt heo. Động vật khác phải trưởng thành, giết 1 nhát và niệm 'bismillah allahu akbar'."));
        pages.add(Text.literal("4. Combat: Không tấn công trước. Không chạy quá 7 block khi chiến đấu."));
        pages.add(Text.literal("5. Salah: Tuân thủ chu kỳ cầu nguyện. Thực hiện Wudu bằng nước và hướng Bắc."));
        pages.add(Text.literal("6. Haram Loot: Cấm lấy đồ từ rương tự nhiên. Cấm thuốc, enchant và giam cầm dân làng."));

        WrittenBookContent content = new WrittenBookContent(
            Text.literal("Luật Lệ & Hướng Dẫn"),
            Text.literal("Imam Khang"),
            pages
        );

        book.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);
        book.set(DataComponentTypes.CUSTOM_DATA, net.minecraft.component.type.NbtComponent.create(
            new net.minecraft.nbt.NbtCompound() {{ put("halal_tutorial_book", 1); }}
        ));

        return book;
    }
}

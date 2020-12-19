package bjfu.it.lixin.bookstore;

public class Magazine {
    private String name;
    private String description;
    private int imageResourceId;

    public Magazine(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public static final Magazine[] magazines ={
            new Magazine("《时尚芭莎》","《时尚芭莎》是一本服务于中国精英女性阶层的时尚杂志，传播来自时装、美和女性的力量。" +
                    "她不仅提供最新的时尚资讯；精辟的流行趋势报道；最受关注的人物专访和女性话题；" +
                    "还时刻与读者分享着当代女性生活的乐趣和智慧——做个现代、优雅代言人。",R.drawable.basha),
            new Magazine("《读者》","《读者》原称《读者文摘》，发掘人性中的真、善、美，体现人文关怀，" +
                    "在刊物内容及形式方面与时俱进，追求高品位、高质量，力求精品，" +
                    "形式和内容具有丰富性及多样性，被誉为“中国人的心灵读本”。",R.drawable.duzhe),
            new Magazine("《中国国家地理》","《中国国家地理》是一本关于地理的期刊，" +
                    "因该社隶属中国科学院，有一大批自然地理和人文地理的专家学者作为该社顾问，" +
                    "同时还有许多战斗在科考第一线的工作者与杂志社保持着密切的联系，因此具有很强的独家性和权威性。" +
                    "该刊的文章和图片经常被中央及地方媒体转载。",R.drawable.dili),
    };

    public String toString(){
        return this.name;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getImageResourceId() { return imageResourceId; }
}

package bjfu.it.lixin.bookstore;

public class Newspaper {
    private String name;
    private String description;
    private int imageResourceId;

    public Newspaper(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public static final Newspaper[] newspapers ={
            new Newspaper("《人民日报》","人民日报作为党和政府的喉舌，作为中国对外文化交流的重要窗口，" +
                    "作为展现蓬勃发展社会主义新中国的舞台，人民日报积极宣传党和政府的政策主张，" +
                    "记录中国社会的变化，报道中国正在发生的变革。",R.drawable.renmin),
            new Newspaper("《新京报》","《新京报》是中国第一次两个党报报业集团合作办报，" +
                    "是中国第一家得到国家有关部门正式批准的跨地区经营的报纸，是一份高度密集覆盖北京市场的强势新主流纸质媒体。",R.drawable.xinjing),
            new Newspaper("《经济观察报》","《经济观察报》是全新商业资讯平台，经济观察网冷静理智的报道风格，" +
                    "并糅合最新的网络技术，拥有专业的采编力量以及独家的新闻报道，提供及时、便捷、专业的信息服务。",R.drawable.jingji),
    };

    public String toString(){
        return this.name;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getImageResourceId() { return imageResourceId; }
}

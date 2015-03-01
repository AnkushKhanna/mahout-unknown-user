package ex.recommender.data;

public class AnonymousPrediction{
	private long userId;
	private long itemId;
	public AnonymousPrediction(long userId, long itemId) {
		this.userId = userId;
		this.itemId = itemId;
	}
	public long getUserId() {
		return userId;
	}
	public long getItemId() {
		return itemId;
	}
}

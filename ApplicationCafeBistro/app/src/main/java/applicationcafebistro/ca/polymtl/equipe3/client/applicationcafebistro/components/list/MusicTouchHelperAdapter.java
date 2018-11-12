package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

/**
 * this interface is used to know some function in the TouchHelperAdapter
 * the class MusicTouchHelperAdapter implements it
 */
public interface MusicTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}

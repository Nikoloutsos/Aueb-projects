package Model;

public class SwapInfo
{
    public BlockInfo first, second;

    public SwapInfo(BlockInfo first, BlockInfo second)
    {
        this.first = first;
        this.second = second;
    }

    public SwapInfo invert()
    {
        return new SwapInfo(second, first);
    }
}

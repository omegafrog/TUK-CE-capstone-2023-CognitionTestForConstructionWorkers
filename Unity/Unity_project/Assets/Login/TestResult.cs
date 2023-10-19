using System;

[Serializable]
public class TestResult
{
    [Serializable]
    public class Twohand
    {
        public bool isPassed;
        public int failedCount;
        public double minResponseTime;
        public Twohand(bool isPassed, int failedCount, double minResponseTime)
        {
            this.isPassed = isPassed;
            this.failedCount = failedCount;
            this.minResponseTime = minResponseTime;
        }
    }
    [Serializable]
    public class Conveyor
    {
        public bool isPassed;
        public double elapsedTime;
        public Conveyor(bool isPassed, double elapsedTime)
        {
            this.isPassed = isPassed;
            this.elapsedTime = elapsedTime;
        }
    }
    [Serializable]
    public class DigitSpan
    {
        public bool isPassed;
        public DigitSpan(bool isPassed) { this.isPassed = isPassed; }
    }
    [Serializable]
    public class DecisionMaking
    {
        public bool isPassed;
        public double minResponseTime;
        public int missedGo;
        public int missClickedNoGo;
        public DecisionMaking(bool isPassed, double minResponseTime, int missedGo, int missClickedNoGo)
        {
            this.isPassed = isPassed;
            this.minResponseTime = minResponseTime;
            this.missedGo = missedGo;
            this.missClickedNoGo = missClickedNoGo;
        }
    }
    [Serializable]
    public class Maze
    {
        public int collisionCount;
        public bool isPassed;
        public Maze(bool isPassed, int collisionCount)
        {
            this.isPassed = isPassed;
            this.collisionCount = collisionCount;
        }
    }
    public Twohand twoHandResult;
    public Conveyor conveyorResult;
    public DigitSpan digitSpanResult;
    public Maze mazeResult;
    public DecisionMaking decisionMakingResult;

    public TestResult(Twohand twoHandResult, Conveyor conveyorResult, DigitSpan digitSpanResult, Maze mazeResult, DecisionMaking decisionMakingResult)
    {
        this.twoHandResult = twoHandResult;
        this.conveyorResult = conveyorResult;
        this.digitSpanResult = digitSpanResult;
        this.mazeResult = mazeResult;
        this.decisionMakingResult = decisionMakingResult;
    }
}
